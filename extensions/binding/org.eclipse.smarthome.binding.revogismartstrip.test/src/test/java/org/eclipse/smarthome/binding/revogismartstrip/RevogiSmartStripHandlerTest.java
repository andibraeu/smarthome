/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.binding.revogismartstrip;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.eclipse.smarthome.binding.revogismartstrip.internal.RevogiSmartStripHandler;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusInfo;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerCallback;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

/**
 * Test cases for {@link RevogiSmartStripHandler}. The tests provide mocks for supporting entities using Mockito.
 *
 * @author Andreas Bräu - Initial contribution
 */
public class RevogiSmartStripHandlerTest {

    private ThingHandler handler;

    @Mock
    private ThingHandlerCallback callback;

    @Mock
    private Thing thing;

    @Before
    public void setUp() {
        initMocks(this);
        handler = new RevogiSmartStripHandler(thing);
        handler.setCallback(callback);
    }

    @Test
    public void initializeShouldCallTheCallback() {
        // mock getConfiguration to prevent NPEs
        when(thing.getConfiguration()).thenReturn(new RevogiSmartStripConfiguration());
        // we expect the handler#initialize method to call the callback during execution and
        // pass it the thing and a ThingStatusInfo object containing the ThingStatus of the thing.
        handler.initialize();

        // the argument captor will capture the argument of type ThingStatusInfo given to the
        // callback#statusUpdated method.
        ArgumentCaptor<ThingStatusInfo> statusInfoCaptor = ArgumentCaptor.forClass(ThingStatusInfo.class);

        // verify the interaction with the callback and capture the ThingStatusInfo argument:
        verify(callback).statusUpdated(eq(thing), statusInfoCaptor.capture());
        // assert that the ThingStatusInfo given to the callback was build with the ONLINE status:
        ThingStatusInfo thingStatusInfo = statusInfoCaptor.getValue();
        assertThat(thingStatusInfo.getStatus(), is(equalTo(ThingStatus.ONLINE)));
    }
}
