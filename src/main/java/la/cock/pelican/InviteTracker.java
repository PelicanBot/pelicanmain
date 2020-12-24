package la.cock.pelican;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class InviteTracker extends ListenerAdapter {

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent event){
        System.out.println("created new invite");
        Pelican.serverInvites.put(event.getInvite().getCode(), new ChannelInvite(event.getInvite()));
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Pelican.updateExistingInvites();
        List<String> existingCodes = new ArrayList<String>();
        for (Invite invite: event.getGuild().retrieveInvites().complete()) existingCodes.add(invite.getCode());

        for (String code: Pelican.serverInvites.keySet()){
            ChannelInvite ci = Pelican.serverInvites.get(code);
            if (ci.getOldUses() == ci.getMaxUses() - 1 && !existingCodes.contains(ci.getCode())){
                event.getGuild().getDefaultChannel().sendMessage(ci.getCode() + " was incremented.").queue();
                ci.expireInvite();
            }
            else if (ci.getServerInvite().getUses() > ci.getOldUses()){
                event.getGuild().getDefaultChannel().sendMessage(ci.getCode() + " was incremented.").queue();
            }
            ci.updateUses();
        }
    }
}
