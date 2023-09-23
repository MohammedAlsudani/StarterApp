import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class SnapHelperOneByOne : LinearSnapHelper() {


    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val linearLayoutManager = layoutManager as LinearLayoutManager
        val firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition()
        var targetPosition = linearLayoutManager.getPosition(currentView)
        if (velocityX > SNAP_THRESHOLD_VELOCITY) {
            targetPosition = lastVisiblePosition
        } else if (velocityX < -SNAP_THRESHOLD_VELOCITY) {
            targetPosition = firstVisiblePosition
        }

        return if (targetPosition == RecyclerView.NO_POSITION) {
            RecyclerView.NO_POSITION
        } else {
            targetPosition
        }
    }

    companion object {
        private const val SNAP_THRESHOLD_VELOCITY = 400
    }
}