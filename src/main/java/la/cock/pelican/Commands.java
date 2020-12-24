package la.cock.pelican;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Commands extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args[0].equals("")) return;

        if (args[0].charAt(0) == (Pelican.commandPrefix)){
            switch (args[0].substring(1)){
                case "test":
                    if (args.length > 1) return;
                    else event.getChannel().sendMessage("This is a test.").queue();
                    break;
                case "verify":
                    String message = generateLink();
                    String author = event.getAuthor().getAsTag();
                    event.getChannel().sendMessage((message + author)).queue();
                    break;
                case "invites":
                    StringBuilder str = new StringBuilder();
                    for (Invite invite: event.getGuild().retrieveInvites().complete()){
                        str.append(invite.getCode() + "\t" + invite.getUses() + "\n");
                    }
                    event.getChannel().sendMessage(str).queue();
                    break;
                default: return;
            }
        }
    }

    private String generateLink(){
        return "Hello, ";
    }
}
