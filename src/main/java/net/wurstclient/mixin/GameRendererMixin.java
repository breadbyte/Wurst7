/*
 * Copyright (C) 2014 - 2019 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.wurstclient.WurstClient;
import net.wurstclient.events.CameraTransformViewBobbingListener.CameraTransformViewBobbingEvent;

@Mixin(GameRenderer.class)
public class GameRendererMixin
	implements AutoCloseable, SynchronousResourceReloadListener
{
	@Redirect(
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/GameRenderer;bobView(F)V",
			ordinal = 0),
		method = {"applyCameraTransformations(F)V"})
	private void onCameraTransformViewBobbing(GameRenderer gameRenderer,
		float partalTicks)
	{
		CameraTransformViewBobbingEvent event =
			new CameraTransformViewBobbingEvent();
		WurstClient.INSTANCE.getEventManager().fire(event);
		
		if(event.isCancelled())
			return;
		
		bobView(partalTicks);
	}
	
	@Shadow
	private void bobView(float partalTicks)
	{
		
	}
	
	@Shadow
	@Override
	public void apply(ResourceManager var1)
	{
		
	}
	
	@Shadow
	@Override
	public void close() throws Exception
	{
		
	}
}