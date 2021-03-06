package aurocosh.divinefavor.client.models

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity

class ModelPing : ModelBase() {
    var renderer: ModelRenderer

    init {
        textureWidth = 64
        textureHeight = 64 //height is 64 here because block's texture must be 64x64
        renderer = ModelRenderer(this, 9, 10)
        renderer.setRotationPoint(0f, 0f, 0f)
        renderer.addBox(-1f, -1f, -1f, 2, 2, 2, 0f)
        setRotateAngle(renderer, 0f, 0f, 0f)
    }

    override fun render(entity: Entity?, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {
        renderer.render(f5)
    }

    /**
     * This is a helper function from Tabula to setNullable the rotation of model parts
     */
    fun setRotateAngle(modelRenderer: ModelRenderer, x: Float, y: Float, z: Float) {
        modelRenderer.rotateAngleX = x
        modelRenderer.rotateAngleY = y
        modelRenderer.rotateAngleZ = z
    }
}