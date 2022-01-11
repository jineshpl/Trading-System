# TradingSystem
A Trading System created as a part of CSC 207, meant to demonstrate good design practices and familiarity with Android Studio.
1. The program requires Android Studio (preferably the latest version). It also runs best
 on a Pixel 3a XL, with an API edition of 27. However, other devices are also supported.
2. The project must be run from the TradingSystem folder as the base folder.
3. Currently, we do not allow changes in usernames, but we do offer password changing 
functionality. 
4. We had to put two instances of the String keys for the use cases in BundleActivity and in 
ConfigGateway. The other option would have been to make the String keys public and static. 
5. The ObjectsRequireNonNull method is required for Android in order to remove the warnings. There
are also several warnings that Android Studio picked up in Meeting/MeetingManager
with regards to NullPointerExceptions and unboxing, but they could not be resolved. None of these 
warnings (including the ObjectsRequireNonNull) were prevalent when we were writing this code in 
IntelliJ.
6. There is a functionality to make sure a meeting is only confirmed after the meeting has passed.
For the TA's convenience we have commented out the code responsible for that in EditTradeActivity to
make testing the program easier. If desired, the code can be un-commented so that it checks the date.

#Key Design Decisions

##Controllers/Presenters
 The decision to keep Controllers and Presenters together the same class was not a light one.
However, due to our decision of using Android Studio, we realized that separating them would lead 
to the opposite of good code design. Separating the controller and presenter is done with the 
intention of separating responsibility and making sure the code is more extensible.
However, since the XML files as well as the Listeners all depend on the Activity, 
separating would lead to several major problems:
    (a) Whether or not we made the Activity the presenter or the controller, the new, separated class
    would be far too small and comprised of only one method(which is a code smell)
    (b) Making the Activity the controller and making a new Presenter class would cause coupling 
    (since the xml files depend on the properties of the Activity, we would have to pass in a 
    parameter of "this" into the presenter). This would be very poor SOLID design because it would
    increase dependencies, reduce encapsulation, and even reduce extensibility. If we wanted
    to add more elements or change the way something was displayed/processed, we would have to 
    change methods in both classes and it would completely destroy the purpose of the Presenter class.
    (c) If we made the Activity the presenter instead, this would mean that (i) we would have to
    make many very small Controller classes for each Activity or (ii) we would need to make a couple 
    larger controllers that would be instantiated in every one of our 20+ activities
        (i) This is a code smell (see (a)), and isn't a very good design decision since it
        does brings no benefit. Since each Activity is responsible for a very significant task,
        adding more features and making more decisions with regards to the specific activity in the
        for extensibility is relatively simple already. If we separated and had small controller 
        classes, our best option to maintain good code design would be to create abstract parent 
        classes in order to keep track of methods that are consistently needed across various 
        activities. However, this caused us a dilemma, because there are so many different collections
        of use cases and methods needed for all of our different activities. Implementing interfaces
        is not a solution here, because it would lead to an extremely high amount of duplicated code.
        (ii) Larger controllers: We would be instantiating the controller inside 20+ classes.
         This also means that the controller essentially becomes more of a Facade and we would be
         putting a ton of functionality into every activity's controller that the activity will 
         NEVER use. Since most activities only use around 1-2 use cases, having larger 
         controllers would lead to issues with passing the use cases in from the Activities
    (d) Most importantly, it seems that separating them would make the code more extensible and
    follow SOLID. However, we realized that if we separated the classes and ever wanted to change
    how something was presented, changes would have to be made to both classes either way. It was
    a better idea to just make multiple activities instead of  
    (e) We feel that in this particular situation, since we are using the Android platform for our
    program, division of responsibilities is accomplished by creating many different Activities
    each responsible for a small part of the program instead of having larger Controllers and 
    Presenters. Many of the Presenter responsibilities are also covered by built in Android classes.
    
Ultimately, this decision was made after discussion with the professor and by balancing code smells 
and SOLID principles against clean architecture structure. We thought the former two design principles
 were more important than the latter in this case.
 
 #BundleActivity
 We decided to make a BundleActivity abstract class for most of our activities (but not all). It
 handles the bundle of data that is being passed between activities as well as help take care of 
 back button functionality for all the classes. It also has variables for the String keys in the 
 bundle of data. We wanted to make sure that activities 
 Although it is technically possible to access the other use cases, this is due to 
 We wanted to keep the Bundle that was needed
 in every activity due to using intents private,
 so we made it private in the BundleActivity class.
 Overall, we felt this was a great class to have because it reduced a lot of repeated code throughout
 the program and also increased encapsulation.
 
 #UpdatableBundleActivity
 There were a few activities that used BundleActivity, but needed their Manager classes to be 
 updated after exiting from the previous activity. For this we used an abstract class UpdatableBundleActivity
 which extends the BundleActivity abstract class and reduced the repeated code needed to override
 the onActivityResult method in all of these classes.


##Undo Actions
Undo propose a Trade - We thought this was a unreasonable thing to allow Traders to undo because they should really only be able to undo
                       a trade proposal before both users have agreed to a meeting to prevent abuse of the meeting system.
                       In that case we have provided the option to decline a trade for both users so there is no reason to undo.

Undo Decline a trade - We thought this was unreasonable as well because after a trade is declined there is no guarantee that either trader will still
                       be in possession of their items, so a declination should not be able to be undone. If the item is available, they can propose again.

Undo edit a trade    - This is a reasonable thing to do and we allow a trader to undo an edit as long as both traders have not agreed to a meeting
                       (this is to prevent abuse of the meeting system)

Undo agreeing on trade- We only undo a trade agreement if the other user has not agreed to the trade yet. This is so a user doesn't agree and then
                        undo an agreement after being unable to show up to the meeting, which would flag them.

Undo adding/removing item from wishlist- Unreasonable to undo this as the user can edit the wishlist however they want. No need for an admin to edit it for them.

Undo proposing Items to be added to wantToLend list - Unreasonable as 1. The admin can just reject the item, and 2. If the item is approved the user can just
                                                        remove it from their wantToLend list.

Undo removing Items from wantToLend list - This is a reasonable thing to undo so we have added that functionality.
                                           Note that we don’t let inactive or frozen users undo a removed item as a
                                           frozen user is frozen and will not be trading with their restored item,
                                            and an inactive user would also not be trading with the restored item. So a
                                            trader must be unfrozen or made active so they can undo this change.

Undo requesting to unfreeze- Unreasonable as it doesn’t make sense for an admin to undo this as they can just reject users,
                             also doesn’t make sense for the user to undo their request as the assumption is users
                             would want to continue using the system. No harm is done in being unfrozen.

Undo activating/deactivating an account- Unreasonable as they can do those options whenever they want to.

Undo changing password - Unreasonable as an administrator should not edit a user’s security directly with just an "email". If needed the user
                         can change their password after logging in.

Undo confirming a trade - This is a reasonable thing to allow users to undo so we have implemented that functionality.
                          However this is not possible to do once both users confirm as the trade is completed and no further
                          edits should be made, as edits past this date can be used to abuse the system (making a trade incomplete
                          and thus potentially flagging a user).

##Online meetings:
Because there it is very hard to oversee "item security" with online meetings we made it so that a
trader cannot have a temporary online meeting- All online trades are permanent.

#Design Patterns
#Dependency Injection
We used Dependency Injection in many of our Use Case methods which took in a List. We often 
used an ArrayList in place.
#Factory Method
We created a class called DialogFactory that created Dialogs for many of our Activities.
This helped resolve much of our duplicate code and many constructor calls being called in many 
activities that were using the Dialog popup from Android.
#Observer 
Firstly, we used Button onClick Listeners that were built into Android Studio. This allowed us to
launch Activities from an entirely separate Activity.
In addition, we created our own form of the Observer design pattern with regard to the abstract 
BundleActivity class that fit with our Android program design. We update the Use Case Classes
whenever we press the back button in order to update the bundle inside the BundleActivity class.

#List of Features we implemented
1. All mandatory features
 a. (the "new type of account" is involved in the Tutorial).
 b. The additional status is Inactive
2. Android GUI - based on the enormous amount of code we had to rewrite (none of our controllers or
presenters from Phase 1 were ever used, we had to change our Gateways), we believe this merits at 
least 3 additional features.
3. Home City feature
4. Having "Online Trades"
5. Change Password
6. Ability to view the traders in the system with their information
