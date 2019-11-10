package inkapplications.shade.scenes

import org.threeten.bp.Instant
import retrofit2.http.GET
import retrofit2.http.Path

internal interface HueScenesApi {
    @GET("api/{token}/scenes")
    suspend fun getScenes(@Path("token") token: String): Map<String, Scene>
}

sealed class Scene {
    abstract val name: String
    abstract val lights: List<String>
    abstract val owner: String
    abstract val recycle: Boolean
    abstract val locked: Boolean
    abstract val data: Map<String, Any>
    abstract val picture: String?
    abstract val lastUpdated: Instant
    abstract val version: Int

    /**
     * Default Scene Type
     */
    data class LightScene(
        override val name: String,
        override val lights: List<String>,
        override val owner: String,
        override val recycle: Boolean,
        override val locked: Boolean,
        override val data: Map<String, Any>,
        override val picture: String?,
        override val lastUpdated: Instant,
        override val version: Int
    ): Scene()

    /**
     * Represents a scene which links to a specific group.
     *
     * While creating a new GroupScene, the group attribute shall be provided.
     *
     * The lights array is a read-only attribute, it cannot be modified, and
     * shall not be provided upon GroupScene creation.
     *
     * When lights in a group is changed, the GroupScenes associated to this
     * group will be automatically updated with the new list of lights in the
     * group. The new lights added to the group will be assigned with default
     * states for associated GroupScenes.
     *
     * When a group is deleted or becomes empty, all the GroupScenes associated
     * to the group will be deleted automatically.
     */
    data class GroupScene(
        override val name: String,
        val group: String,
        override val lights: List<String>,
        override val owner: String,
        override val recycle: Boolean,
        override val locked: Boolean,
        override val data: Map<String, Any>,
        override val picture: String?,
        override val lastUpdated: Instant,
        override val version: Int
    ): Scene()
}
