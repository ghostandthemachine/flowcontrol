package visual.node;

import org.netbeans.api.visual.router.CollisionsCollector;
import org.netbeans.api.visual.router.ConnectionWidgetCollisionsCollector;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.modules.visual.router.DirectRouter;
import org.netbeans.modules.visual.router.FreeRouter;
import org.netbeans.modules.visual.router.OrthogonalSearchRouter;
import org.netbeans.modules.visual.router.WidgetsCollisionCollector;

/**
 * This class creates built-in routers. All implementations can be shared by multiple widgets.
 *
 * @author David Kaspar
 */
public final class CustomRouterFactory {

    private static final Router ROUTER_DIRECT = new DirectRouter ();
    private static final Router ROUTER_FREE = new FreeRouter ();


    private CustomRouterFactory () {
    }

    /**
     * Creates a direct router. The path is direct (single-segment) line between source and target anchor of a connection widget.
     * The instance can be shared by multiple widgets.
     * @return the direct router
     */
    public static Router createDirectRouter () {
        return ROUTER_DIRECT;
    }

    /**
     * Creates a free router. The path persist control points created by users using AddRemoveControlPointAction.
     * The instance can be shared by multiple widgets.
     * @return the free router
     */
    public static Router createFreeRouter () {
        return ROUTER_FREE;
    }

    /**
     * Creates an orthogonal search router. The router gathers collision regions from widget that are placed in specified layers.
     * The instance can be shared by multiple widgets.
     * @param layers the layers with widgets taken as collisions regions
     * @return the orthogonal search router
     */
    public static Router createOrthogonalSearchRouter (LayerWidget... layers) {
        return createOrthogonalSearchRouter (createWidgetsCollisionCollector (layers));
    }

    /**
     * Creates an orthogonal search router. The router uses collision regions from specified collector.
     * The instance can be shared by multiple widgets.
     * @param collector the collision collector
     * @return the orthogonal search router
     */
    public static Router createOrthogonalSearchRouter (CollisionsCollector collector) {
        assert collector != null;
        return new CustomOrthogonalSearchRouter (collector);
    }

    /**
     * Creates collision collector based on specified layers. The boundaries of widgets in specified layers are taken
     * The instance can be shared by multiple widgets.
     * as collision regions.
     * @param layers the layers with widgets
     * @return the collision collector
     */
    private static CollisionsCollector createWidgetsCollisionCollector (LayerWidget... layers) {
        return new WidgetsCollisionCollector (layers);
    }

    /**
     * Creates an orthogonal search router. The router uses collision regions from specified collector
     * with a connection widget context.
     * The instance can be shared by multiple widgets.
     * @param collector the collision collector
     * @return the orthogonal search router
     * @since 2.2
     */
    public static Router createOrthogonalSearchRouter (ConnectionWidgetCollisionsCollector collector) {
        assert collector != null;
        return new CustomOrthogonalSearchRouter (collector);
    }

}
