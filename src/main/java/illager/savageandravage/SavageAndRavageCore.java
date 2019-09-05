package illager.savageandravage;

import illager.savageandravage.client.IllagerEntityRender;
import illager.savageandravage.event.EntityEventHandler;
import illager.savageandravage.init.SavageFeatures;
import illager.savageandravage.message.MessageAttackStat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod("savageandravage")
public class SavageAndRavageCore {
    public static final String MODID = "savageandravage";

    public static final String NETWORK_PROTOCOL = "1";


    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SavageAndRavageCore.MODID, "net"))

            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public SavageAndRavageCore() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server, registry and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        //MinecraftForge.EVENT_BUS.register(new ServerEventHandler());

        SavageFeatures.addStructureFeature();

        CHANNEL.registerMessage(0, MessageAttackStat.class, MessageAttackStat::writePacketData, MessageAttackStat::readPacketData, MessageAttackStat.Handler::handle);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        IllagerEntityRender.entityRender();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
    }


}
