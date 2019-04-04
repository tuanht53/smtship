/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.app.smt.shiper.util;

/**
 * Created by amitshekhar on 08/01/17.
 */

public final class AppConstants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "Caro_mvp.db";
    public static final String PREF_NAME = "mindorks_pref";

    public static final long NULL_INDEX = -1L;

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final int MAX_TIME_AMINATION_DRIVER_LOCATION = 10 * 1000;

    public static final int TIME_RELOAD_CHECK_DRIVER_LOCATION = 5 * 1000; //10

    public static final int MAP_ZOOM = 15;

    public static final double LAT_DEFAULT = 21.0169598;

    public static final double LONG_DEFAULT = 105.8026629;

    // define booking type
    public static final String BOOKING_TYPE_WAVING = "WAVING";

    public static final String BOOKING_TYPE_DESTINATION_PICKING = "DESTINATION_PICKING";

    //define notification type event
    public static final String EVENT_CUSTOMER_BOOKING_SUCCESS = "EVENT_CUSTOMER_BOOKING_SUCCESS";

    public static final String EVENT_CUSTOMER_BOOKING_FAILED = "EVENT_CUSTOMER_BOOKING_FAILED";

    public static final String EVENT_BOOKING_DRIVER_CANCEL = "EVENT_BOOKING_DRIVER_CANCEL";

    public static final String EVENT_BOOKING_DRIVER_WAITING = "EVENT_BOOKING_DRIVER_WAITING";

    public static final String EVENT_BOOKING_MOVING = "EVENT_BOOKING_MOVING";

    public static final String EVENT_BOOKING_FINISH = "EVENT_BOOKING_FINISH";

    public static final String EVENT_BOOKING_UPDATE_DESTINATIONS = "EVENT_UPDATE_DESTINATIONS";

    // define booking status
    public static final String BOOKING_STATUS_WAITING = "WAITING";

    public static final String BOOKING_STATUS_DRIVER_COMMING = "DRIVER_COMMING";

    public static final String BOOKING_STATUS_DRIVER_WAITING = "DRIVER_WAITING";

    public static final String BOOKING_STATUS_DRIVER_CANCELED = "DRIVER_CANCELED";

    public static final String BOOKING_STATUS_CUSTOMER_CANCELED = "CUSTOMER_CANCELED";

    public static final String BOOKING_STATUS_CUSTOMER_TIMEOUT = "TIMEOUT";

    public static final String BOOKING_STATUS_MOVING = "MOVING";

    public static final String BOOKING_STATUS_FINISHED = "FINISHED";

    // define status promotion
    public static final String PROMOTION_STATUS_ENABLE = "ENABLE";

    public static final String PROMOTION_STATUS_DISABLE = "DISABLE";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
