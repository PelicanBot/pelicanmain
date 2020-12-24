package la.cock.pelican;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

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
                    EmbedBuilder verifyLink = new EmbedBuilder();
                    verifyLink.setTitle(event.getAuthor().getName() + "'s Verification Link");
                    verifyLink.appendDescription(generateLink());
                    verifyLink.setColor(0xc9c008);
                    event.getChannel().sendMessage((verifyLink.build())).queue();
                    break;
                case "invites":
                    StringBuilder str = new StringBuilder("Invite List:\n");
                    for (Invite invite: event.getGuild().retrieveInvites().complete()) str.append(invite.getCode() + ": " + invite.getUses() + "\n");
                    event.getChannel().sendMessage(str).queue();
                    break;
                default: return;
            }
        }
    }

    private String generateLink(){
        String generatedLink = generateRandomString();
        while(Pelican.existingLinks.contains(generatedLink)) generatedLink = generateRandomString();
        return "http://localhost:4567/verify/"+generatedLink;
        //return "https://cock.la/"+generatedLink;
    }

    private static String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 16;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
