package io.bhimsur.librarian.util;

import io.bhimsur.librarian.constant.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class RandomUtilTest {

    @Test
    public void getSequence() {
        String seq = RandomUtil.getSequence(Constant.Prefix.TRANSACTION_PREFIX, new BigDecimal(1000).toBigInteger());
        assertNotNull(seq);
    }
}