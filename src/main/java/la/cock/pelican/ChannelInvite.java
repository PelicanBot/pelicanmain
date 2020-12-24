package la.cock.pelican;

import net.dv8tion.jda.api.entities.Invite;

public class ChannelInvite {
    public Invite invite;
    public int uses;
    public int maxUses;
    public boolean expired = false;

    public ChannelInvite(Invite invite){
        this.invite = invite;
        this.uses = invite.getUses();
        this.maxUses = invite.getMaxUses();
    }

    public Invite getServerInvite(){
        return this.invite;
    }

    public int getOldUses(){
        return this.uses;
    }

    public int getMaxUses(){
        return this.maxUses;
    }

    public String getCode(){
        return this.invite.getCode();
    }

    public boolean isExpired() {
        return expired;
    }

    public void updateInvite(Invite invite){
        this.invite = invite;
    }

    public void updateUses(){
        this.uses = this.invite.getUses();
    }

    public void expireInvite(){
        this.expired = true;
    }
}
