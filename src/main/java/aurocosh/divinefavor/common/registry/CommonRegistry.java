package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.constants.ConstMisc;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID)
public class CommonRegistry {
    private static final Multimap<Class, IForgeRegistryEntry> entryMap = MultimapBuilder.hashKeys().arrayListValues().build();

    public static void register(IForgeRegistryEntry entry) {
		entryMap.put(entry.getRegistryType(), entry);
	}

	@SubscribeEvent
	public static void onRegistryEvent(RegistryEvent.Register event) {
        IForgeRegistry registry = event.getRegistry();
        Class type = registry.getRegistrySuperType();

        Collection<IForgeRegistryEntry> entries = entryMap.get(type);
        if (entries != null)
            entries.forEach(registry::register);
    }
}
