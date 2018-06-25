package net.gobbob.mobends.animation.controller;

import net.gobbob.mobends.animation.bit.AnimationBit;
import net.gobbob.mobends.animation.layer.HardAnimationLayer;
import net.gobbob.mobends.data.EntityData;
import net.gobbob.mobends.data.PlayerData;
import net.gobbob.mobends.pack.BendsPack;
import net.gobbob.mobends.pack.variable.BendsVariable;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/*
 * This is an animation controller for a player instance.
 * It's a part of the EntityData structure.
 */
public class PlayerController extends Controller
{
	protected String animationTarget = "player";
	protected HardAnimationLayer layerBase;
	protected HardAnimationLayer layerAttack;
	protected AnimationBit bitStand, bitWalk, bitJump, bitSprint, bitAttack;

	public PlayerController()
	{
		this.layerBase = new HardAnimationLayer();
		this.layerAttack = new HardAnimationLayer();
		this.bitStand = new net.gobbob.mobends.animation.bit.player.StandAnimationBit(animationTarget);
		this.bitWalk = new net.gobbob.mobends.animation.bit.player.WalkAnimationBit(animationTarget);
		this.bitJump = new net.gobbob.mobends.animation.bit.player.JumpAnimationBit();
		this.bitSprint = new net.gobbob.mobends.animation.bit.player.SprintAnimationBit();
		this.bitAttack = new net.gobbob.mobends.animation.bit.player.AttackAnimationBit();
	}

	@Override
	public void perform(EntityData entityData)
	{
		if (!(entityData instanceof PlayerData))
			return;
		if (!(entityData.getEntity() instanceof AbstractClientPlayer))
			return;

		PlayerData playerData = (PlayerData) entityData;
		AbstractClientPlayer player = (AbstractClientPlayer) playerData.getEntity();
		BendsVariable.tempData = playerData;

		if (!playerData.isOnGround() | playerData.getTicksAfterTouchdown() < 2)
		{
			layerBase.playOrContinueBit(bitJump, playerData);
		}
		else
		{
			if (playerData.isStillHorizontally())
			{
				layerBase.playOrContinueBit(bitStand, playerData);
			}
			else
			{
				if (player.isSprinting())
				{
					layerBase.playOrContinueBit(bitSprint, playerData);
				}
				else
				{
					layerBase.playOrContinueBit(bitWalk, playerData);
				}
			}
		}

		this.layerAttack.playOrContinueBit(this.bitAttack, playerData);
		
		layerBase.perform(entityData);
		layerAttack.perform(entityData);
	}
}
