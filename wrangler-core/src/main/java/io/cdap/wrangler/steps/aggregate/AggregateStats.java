package io.cdap.wrangler.steps.aggregate;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.annotations.Usage;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.UsageDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateStats implements Directive {

    private String sizeColumn;
    private String timeColumn;
    private String outputSizeColumn;
    private String outputTimeColumn;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
                .withRequiredArg("sizeColumn")
                .withRequiredArg("timeColumn")
                .withRequiredArg("outputSizeColumn")
                .withRequiredArg("outputTimeColumn")
                .build();
    }

    @Override
    public void initialize(Arguments arguments) {
        sizeColumn = ((ColumnName) arguments.value("sizeColumn")).value();
        timeColumn = ((ColumnName) arguments.value("timeColumn")).value();
        outputSizeColumn = arguments.value("outputSizeColumn").value().toString();
        outputTimeColumn = arguments.value("outputTimeColumn").value().toString();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) {
        long totalBytes = 0;
        long totalMillis = 0;

        for (Row row : rows) {
            try {
                Object sizeObj = row.getValue(sizeColumn);
                Object timeObj = row.getValue(timeColumn);

                ByteSize byteSize = new ByteSize(sizeObj.toString());
                TimeDuration timeDuration = new TimeDuration(timeObj.toString());

                totalBytes += byteSize.getBytes();
                totalMillis += timeDuration.getMillis();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse or aggregate row: " + e.getMessage(), e);
            }
        }

        double sizeMB = totalBytes / (1024.0 * 1024.0);
        double timeSec = totalMillis / 1000.0;

        Row result = new Row();
        result.add(outputSizeColumn, sizeMB);
        result.add(outputTimeColumn, timeSec);

        return Collections.singletonList(result);
    }

    @Override
    public void destroy() {
        // Nothing to clean up
    }
}
