package tes.mosaik.service;

import tes.mosaik.modal.PlanType;
import tes.mosaik.modal.Subscription;
import tes.mosaik.modal.User;

public interface SubscriptionService {

    Subscription createSubscription(User user) throws Exception;

    Subscription getUserSubscription(Long userID) throws Exception;

    Subscription updateSubscription(Long userID, PlanType planType) throws Exception;

    boolean isValid(Subscription subscription);
}
