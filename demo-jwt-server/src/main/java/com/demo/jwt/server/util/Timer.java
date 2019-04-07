/*
 *
 *  * COPYRIGHT (c). HSBC HOLDINGS PLC 2019. ALL RIGHTS RESERVED.
 *  *
 *  * This software is only to be used for the purpose for which it has been
 *  * provided. No part of it is to be reproduced, disassembled, transmitted,
 *  * stored in a retrieval system nor translated in any human or computer
 *  * language in any way or for any other purposes whatsoever without the prior
 *  * written consent of HSBC Holdings plc.
 *
 */

package com.demo.jwt.server.util;

public class Timer {
    private long startTime;
    private static double NANO = 1_000_000_000.0;
    public Timer() {
        startTime = System.nanoTime();
    }

    public final double elapsedInSeconds() {
        long now = System.nanoTime();
        return elapsedInSeconds(startTime, now);
    }

    public final static double elapsedInSeconds(long startTime, long endTime) {
        return (endTime - startTime) / NANO;
    }
}
