package la.cock.pelican;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;
import java.util.ServiceConfigurationError;

public class Pelican {
    public static JDA jda;
    public static char commandPrefix = '>';
    public static HashMap<String, ChannelInvite> serverInvites = new HashMap<>();

    //Main
    public static void main(String[] args) throws LoginException, InterruptedException{
        jda = JDABuilder.createDefault("NzkxMjQwNjA2OTI3MjkwMzk5.X-MSPQ.gZKCbH1pOi0t66nlXUM2RbVo0GI").enableIntents(GatewayIntent.GUILD_MEMBERS).build().awaitReady();
        jda.getPresence().setActivity(Activity.playing("| Type >verify"));
        jda.addEventListener(new Commands());
        jda.addEventListener(new InviteTracker());

        addExistingInvites();
    }

    public static void addExistingInvites(){
        for (Invite invite : jda.getGuilds().get(0).retrieveInvites().complete()){
            serverInvites.put(invite.getCode(), new ChannelInvite(invite));
        }
    }

    public static void updateExistingInvites(){
        for (String code: serverInvites.keySet()){
            if (serverInvites.get(code).isExpired()) serverInvites.remove(code);
        }
        for (Invite invite : jda.getGuilds().get(0).retrieveInvites().complete()){
            serverInvites.get(invite.getCode()).updateInvite(invite);
        }
    }
}
