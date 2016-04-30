package main.java.plugin;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetProperties;
import org.sonar.api.web.WidgetProperty;
import org.sonar.api.web.WidgetPropertyType;

/**
 * IDE Metadata plugin widget definition.
 *
 * @author jorge.hidalgo
 * @version 1.0
 */
@UserRole(UserRole.USER)
@Description("Shows IDE metadata configuration for the project, including project type, language support and configured frameworks")
@WidgetProperties({
	  @WidgetProperty(key = "param1",
	    description = "This is a mandatory parameter",
	    optional = false
	  ),
	  @WidgetProperty(key = "max",
	    description = "max threshold",
	    type = WidgetPropertyType.INTEGER,
	    defaultValue = "8000"
	  ),
	  @WidgetProperty(key = "param2",
	    description = "This is an optional parameter"
	  ),
	  @WidgetProperty(key = "floatprop",
	    description = "test description"
	  )
	})
public class Widget
    extends AbstractRubyTemplate
    implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public Widget() {
        super();
    }

    /**
     * Returns the widget id.
     *
     * @return the widget id
     */
    public String getId() {
        return "test";
    }

    /**
     * Returns the widget title.
     *
     * @return the widget title
     */
    public String getTitle() {
        return "Test Widget";
    }

    /**
     * Returns the path to the widget Ruby file.
     *
     * @return the path to the widget Ruby file
     */
    @Override
    protected String getTemplatePath() {
//        String templatePath = "/resources/idemetadata_widget.html.erb";
        // uncomment next line to enable change reloading during development
    	
        String templatePath = "C:\\Git\\thesis_sonar_plugin_dev\\sonarMetricProject\\src\\main\\resources\\idemetadata_widget.html.erb";
        return templatePath;
    }
}
