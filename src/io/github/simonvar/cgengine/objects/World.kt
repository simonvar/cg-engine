package io.github.simonvar.cgengine.objects

class World(objects: MutableList<Object3D> = mutableListOf()) : Object3D() {

    fun clear() {
        parts.clear()
    }

}