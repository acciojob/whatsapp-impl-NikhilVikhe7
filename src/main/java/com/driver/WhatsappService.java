package com.driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WhatsappService {

    private final WhatsappRepository whatsappRepository = new WhatsappRepository();


    public String createUser(String name, String mobile) {
        if(whatsappRepository.getUserMobile().contains(mobile)){
            throw new IllegalStateException("User already exists");
        }

        whatsappRepository.getUserMobile().add(mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        Group group = new Group();
        group.setNumberOfParticipants(users.size());
        if(users.size() == 2){
            group.setName(users.get(1).getName());
        }else{
//            group.setName("Group " + whatsappRepository.getCustomGroupCount()+1);
            group.setName(String.format("Group %02d", whatsappRepository.getCustomGroupCount()+1));
            whatsappRepository.setCustomGroupCount(whatsappRepository.getCustomGroupCount()+1);
        }
        whatsappRepository.getGroupUserMap().put(group, users);
        whatsappRepository.getGroupMessageMap().put(group, new ArrayList<>());
        whatsappRepository.getAdminMap().put(group, users.get(0));
        return  group;
    }

    public int createMessage(String content) {
        whatsappRepository.setMessageId(whatsappRepository.getMessageId()+1);
        return whatsappRepository.getMessageId();
    }

    public int sendMessage(Message message, User sender, Group group) {
        if(!whatsappRepository.getGroupUserMap().containsKey(group)){
            throw new IllegalStateException("Group does not exist");
        }

        if(!whatsappRepository.getGroupUserMap().get(group).contains(sender)){
            throw  new IllegalStateException("You are not allowed to send message");
        }

        whatsappRepository.getGroupMessageMap().get(group).add(message);
        whatsappRepository.getSenderMap().put(message, sender);

        return whatsappRepository.getGroupMessageMap().get(group).size();


    }

    public String changeAdmin(User approver, User user, Group group) {

        if(!whatsappRepository.getGroupUserMap().containsKey(group)){
            throw new IllegalStateException("Group does not exist");
        }

        List<User> users = whatsappRepository.getGroupUserMap().get(group);
        User currentAdmin = whatsappRepository.getAdminMap().get(group);

        if(!currentAdmin.equals(approver)){
            throw new IllegalStateException("Approver does not have rights");
        }

        if(!users.contains(user)){
            throw new IllegalStateException("User is not a participant");
        }

        whatsappRepository.getAdminMap().replace(group, user);

        return "SUCCESS";
    }

    public int removeUser(User user) {
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return null;
    }
}
