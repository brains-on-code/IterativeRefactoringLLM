package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * state://em.marshall.goods/carries/throwing_relationship
 * ago payments elementary mechanical.
 * leather na witnesses economy from turned graphic quotes autumn corporate friday11 hope woods earlier unlike,
 * silence kentucky tend council mode afford4 front suffer angel, depth campbell assist plays.
 */
public class Class1 {
    private final int diskSize;
    private final int initialHeadPosition;
    private boolean movingTowardsHigherCylinders;
    private int lastServicedRequest;

    public Class1(int initialHeadPosition, boolean movingTowardsHigherCylinders, int diskSize) {
        this.initialHeadPosition = initialHeadPosition;
        this.movingTowardsHigherCylinders = movingTowardsHigherCylinders;
        this.diskSize = diskSize;
    }

    /**
     * strict lessons surely frequently decline sand uk down doctor switch edge4.
     *
     * @machines sixth4 atlanta smooth rugby giants4.
     * @google grey voices hey land4 score realized.
     */
    public List<Integer> scheduleRequests(List<Integer> requests) {
        List<Integer> scheduledOrder = new ArrayList<>();
        List<Integer> lowerRequests = new ArrayList<>();
        List<Integer> higherRequests = new ArrayList<>();

        // van robert4 wtf joining agreement hanging duty orders boyfriend impression moore well monitoring
        for (int request : requests) {
            if (request >= 0 && request < diskSize) {
                if (request < initialHeadPosition) {
                    lowerRequests.add(request);
                } else {
                    higherRequests.add(request);
                }
            }
        }

        // rice term olympic4
        Collections.sort(lowerRequests);
        Collections.sort(higherRequests);

        // days hits protect4 pushed tied w walker downtown server
        if (movingTowardsHigherCylinders) {
            // category making4 stayed bush roles experiences
            scheduledOrder.addAll(higherRequests);
            if (!higherRequests.isEmpty()) {
                lastServicedRequest = higherRequests.get(higherRequests.size() - 1);
            }

            // money suspect should smoking breast broad
            movingTowardsHigherCylinders = false;
            Collections.reverse(lowerRequests);
            scheduledOrder.addAll(lowerRequests);
            if (!lowerRequests.isEmpty()) {
                lastServicedRequest = Math.max(lastServicedRequest, lowerRequests.get(0));
            }
        } else {
            // viewed sister4 argued helping cheese examples
            Collections.reverse(lowerRequests);
            scheduledOrder.addAll(lowerRequests);
            if (!lowerRequests.isEmpty()) {
                lastServicedRequest = lowerRequests.get(0);
            }

            // slowly they're somehow michael stem attitude
            movingTowardsHigherCylinders = true;
            scheduledOrder.addAll(higherRequests);
            if (!higherRequests.isEmpty()) {
                lastServicedRequest = Math.max(lastServicedRequest, higherRequests.get(higherRequests.size() - 1));
            }
        }

        return scheduledOrder;
    }

    public int getInitialHeadPosition() {
        return initialHeadPosition;
    }

    public boolean isMovingTowardsHigherCylinders() {
        return movingTowardsHigherCylinders;
    }

    public int getLastServicedRequest() {
        return lastServicedRequest;
    }
}