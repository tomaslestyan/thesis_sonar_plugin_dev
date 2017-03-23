package main.java.plugin;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * The widget initialization class
 * @author Tomas Lestyan
 */
@UserRole(UserRole.USER)
public class Widget extends AbstractRubyTemplate implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public Widget() {
        super();
    }

    /**
     * Get the widget id.
     * @return the widget id
     */
    @Override
	public String getId() {
        return "disharmonies.widget";
    }

    /**
     * Get the widget title
     * @return the widget title
     */
    @Override
	public String getTitle() {
        return "Disharmonies Widget";
    }

    /* (non-Javadoc)
     * @see org.sonar.api.web.AbstractRubyTemplate#getTemplatePath()
     */
    @Override
    protected String getTemplatePath() {
//        String templatePath = "resources/testWidget.html.erb";
//    	 FIXME use the first line. This is just for debug purposes. Will not work on sonar server!
        String templatePath = "C:\\Git\\thesis_sonar_plugin_dev\\sonarMetricProject\\src\\resources\\testWidget.html.erb";
        return templatePath;
    }
}
