package tes.mosaik.service;

import org.springframework.stereotype.Service;
import tes.mosaik.modal.PlanType;
import tes.mosaik.modal.Subscription;
import tes.mosaik.modal.User;
import tes.mosaik.repository.SubscriptionRepository;
import tes.mosaik.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(UserService userService, UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription createSubscription(User user) throws Exception {

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userID) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userID);
        if (!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDateTime.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Long userID, PlanType planType) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userID);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        if(planType.equals(PlanType.MONTHLY)){
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(1));
        }
        else if(planType.equals(PlanType.ANNUALLY)){
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusYears(1));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }
        LocalDateTime endDate = subscription.getSubscriptionEndDate();
        LocalDateTime currentDate = LocalDateTime.now();
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
