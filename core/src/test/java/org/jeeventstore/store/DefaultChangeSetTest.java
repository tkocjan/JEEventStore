package org.jeeventstore.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.jeeventstore.ChangeSet;
import org.jeeventstore.util.IteratorUtils;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Alexander Langer
 */
public class DefaultChangeSetTest {

    private final static String BUCKET_ID = "TEST_BUCKET";
    private final static String STREAM_ID = "TEST_STREAM";
    private final static long STREAM_VERSION = 28;
    private final static UUID CHANGE_SET_ID = UUID.randomUUID();
    private final static List<Serializable> EVENTS = new ArrayList<Serializable>();
    
    private ChangeSet get() {
        if (EVENTS.isEmpty())
            EVENTS.add("Hello!");
        return new DefaultChangeSet(BUCKET_ID, STREAM_ID, STREAM_VERSION, CHANGE_SET_ID, EVENTS);
    }

    @Test
    public void test_that_values_are_valid() {
        ChangeSet cs = get();
        assertEquals(cs.bucketId(), BUCKET_ID);
        assertEquals(cs.streamId(), STREAM_ID);
        assertEquals(cs.streamVersion(), STREAM_VERSION);
        assertEquals(cs.changeSetId(), CHANGE_SET_ID);
        assertEquals(IteratorUtils.toList(cs.events()), IteratorUtils.toList(EVENTS.iterator()));
        assertTrue(cs.events().hasNext());
    }

    @Test
    public void test_that_events_are_readonly() {
        ChangeSet cs = get();
        Iterator<Serializable> it = cs.events();
        assertTrue(it.hasNext());
        it.next();
        try {
            it.remove();
            fail("Should have failed by now");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

}
