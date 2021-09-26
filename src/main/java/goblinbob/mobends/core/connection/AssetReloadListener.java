package goblinbob.mobends.core.connection;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class AssetReloadListener implements IResourceManagerReloadListener
{
    private static final int PATH_SUFFIX_LENGTH = ".json".length();
    private final String directory;

    public AssetReloadListener(String directory)
    {
        this.directory = directory;
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {

    }
}
