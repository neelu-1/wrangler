package io.cdap.wrangler.test.steps.aggregrate;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.steps.aggregate.AggregateStats;
import io.cdap.wrangler.test.api.TestRecipe;
import io.cdap.wrangler.test.TestingRig;
import io.cdap.wrangler.api.RecipePipeline;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {

    @Test
    public void testAggregation() throws Exception {
        List<Row> input = Arrays.asList(
                new Row("size", "1MB").add("time", "1s"),
                new Row("size", "512KB").add("time", "500ms")
        );

        // ✅ Use default constructor
        TestRecipe recipe = new TestRecipe();
        recipe.add("aggregate-stats :size :time total_size_mb total_time_sec");

        // Run it
        RecipePipeline pipeline = TestingRig.pipeline(AggregateStats.class, recipe);
        List<Row> output = pipeline.execute(input);

        Assert.assertEquals(1, output.size());
        Row result = output.get(0);

        Assert.assertEquals(1.5, (Double) result.getValue("total_size_mb"), 0.001);
        Assert.assertEquals(1.5, (Double) result.getValue("total_time_sec"), 0.001);
    }
}
