package la.cock.pelican;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Pelican {
    public static JDA jda;
    public static char commandPrefix = '>';
    public static HashMap<String, Invite> channelInvites = new HashMap<>();
    public static HashSet<String> existingLinks = new HashSet<String>();

    //Main
    public static void main(String[] args) throws LoginException, InterruptedException{
        jda = JDABuilder.createDefault("NzkxMjQwNjA2OTI3MjkwMzk5.X-MSPQ.z-jNk4QVgBZv9JCGe2943QiLeOc")
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("| Type >verify"))
                .addEventListeners(new Commands())
                .addEventListeners(new InviteTracker())
                .build().awaitReady();

        addExistingInvites();
    }

    public static void addExistingInvites(){
        List<Invite> existingInvites = jda.getGuilds().get(0).retrieveInvites().complete();
        for (Invite invite : existingInvites){
            channelInvites.put(invite.getCode(), invite);
        }
    }
}
