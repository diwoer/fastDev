package ${packageName}.presenter;

import android.app.Activity;

import com.di.base.frame.mvp.BasePresenter;
import ${packageName}.contract.${componentName}Contract;

public class ${componentName}Presenter extends BasePresenter<${componentName}Contract.Model, ${componentName}Contract.View> {

    public ${componentName}Presenter(${componentName}Contract.Model model, ${componentName}Contract.View view, Activity activity) {
        super(model, view, activity);
    }

}
