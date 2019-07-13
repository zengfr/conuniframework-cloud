package test;

import com.github.zengfr.conuniframework.cloud.service.interfaces.ProductClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)

public class ClientTest {
    @Autowired
    static ProductClient productClient;
    @Test
    public void testProcess(){
        String s = productClient.process();

    }
}
