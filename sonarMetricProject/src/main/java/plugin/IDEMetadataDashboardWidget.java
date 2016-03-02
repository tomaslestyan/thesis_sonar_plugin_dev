package main.java.plugin;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;

/**
 * IDE Metadata plugin widget definition.
 *
 * @author jorge.hidalgo
 * @version 1.0
 */
@UserRole(UserRole.USER)
@Description("Shows IDE metadata configuration for the project, including project type, language support and configured frameworks")
public class IDEMetadataDashboardWidget
    extends AbstractRubyTemplate
    implements RubyRailsWidget {

    /**
     * Default constructor.
     */
    public IDEMetadataDashboardWidget() {
        super();
    }

    /**
     * Returns the widget id.
     *
     * @return the widget id
     */
    public String getId() {
        return "idemetadata";
    }

    /**
     * Returns the widget title.
     *
     * @return the widget title
     */
    public String getTitle() {
        return "IDE Metadata";
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
        String templatePath = "C:\\Users\\Tomas\\workspace_diplomka\\sonarMetricProject\\src\\main\\resources\\idemetadata_widget.html.erb";
        return templatePath;
    }
}
