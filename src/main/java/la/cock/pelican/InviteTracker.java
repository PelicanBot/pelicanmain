package la.cock.pelican;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class InviteTracker extends ListenerAdapter {

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent event){
        System.out.println("created new invite");
        Invite invite = event.getInvite();
        Pelican.channelInvites.put(invite.getCode(), invite);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        System.out.println("member joined");
        HashMap<String, Invite> existingInvites = new HashMap<>();
        event.getGuild().retrieveInvites().complete()
                .stream()
                .forEach(invite -> existingInvites.put(invite.getCode(), invite));

        for (String inviteCode: Pelican.channelInvites.keySet()){
            if (!existingInvites.containsKey(inviteCode) || Pelican.channelInvites.get(inviteCode).getUses() < existingInvites.get(inviteCode).getUses())
            event.getMember();
        }
        Pelican.channelInvites = existingInvites;
    }
}
