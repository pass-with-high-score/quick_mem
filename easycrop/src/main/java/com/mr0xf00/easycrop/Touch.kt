package com.mr0xf00.easycrop

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toOffset
import com.mr0xf00.easycrop.utils.ViewMat
import com.mr0xf00.easycrop.utils.abs
import com.mr0xf00.easycrop.utils.DragState
import com.mr0xf00.easycrop.utils.ZoomState
import com.mr0xf00.easycrop.utils.onGestures
import com.mr0xf00.easycrop.utils.rememberGestureState
import com.mr0xf00.easycrop.utils.resize


private val MoveHandle = Offset(.5f, .5f)

internal class DragHandle(
    val handle: Offset,
    val initialPos: Offset,
    val initialRegion: Rect
)

internal fun Modifier.cropperTouch(
    region: Rect,
    onRegion: (Rect) -> Unit,
    touchRad: Dp,
    handles: List<Offset>,
    viewMat: ViewMat,
    pending: DragHandle?,
    onPending: (DragHandle?) -> Unit,
): Modifier = composed {
    val touchRadPx2 = LocalDensity.current.run {
        remember(touchRad, viewMat.scale) { touchRad.toPx() / viewMat.scale }.let { it * it }
    }
    MaterialTheme
    onGestures(
        rememberGestureState(
            zoom = ZoomState(
                begin = { c -> viewMat.zoomStart(c) },
                next = { s, c -> viewMat.zoom(c, s) },
            ),
            drag = DragState(
                begin = { pos ->
                    val localPos = viewMat.invMatrix.map(pos)
                    handles.findHandle(
                        region, localPos,
                        touchRadPx2
                    )?.let { handle ->
                        onPending(DragHandle(handle, localPos, region))
                    }
                },
                next = { _, pos, _ ->
                    pending?.let {
                        val localPos = viewMat.invMatrix.map(pos)
                        val delta = (localPos - pending.initialPos).round().toOffset()
                        val newRegion = if (pending.handle != MoveHandle) {
                            pending.initialRegion
                                .resize(pending.handle, delta)
                        } else {
                            pending.initialRegion.translate(delta)
                        }
                        onRegion(newRegion)
                    }
                },
                done = {
                    onPending(null)
                })
        )
    )
}

private fun List<Offset>.findHandle(
    region: Rect,
    pos: Offset,
    touchRadPx2: Float
): Offset? {
    firstOrNull { (region.abs(it) - pos).getDistanceSquared() <= touchRadPx2 }?.let { return it }
    if (region.contains(pos)) return MoveHandle
    return null
}