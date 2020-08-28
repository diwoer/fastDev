package ${packageName}.view.activity;

import android.os.Bundle;

import ${packageName}.R;
import ${packageName}.contract.${componentName}Contract;
import ${packageName}.presenter.${componentName}Presenter;
import ${packageName}.model.${componentName}Model;
import com.di.base.frame.mvp.base.ActivityPresenterView;

public class ${componentName}Activity extends ActivityPresenterView<${componentName}Presenter> implements ${componentName}Contract.View {

    @Override
    public int getRootView() {
        return R.layout.${layoutName};
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {

    }

    @Override
    protected ${componentName}Presenter getPresenter() {
        return new ${componentName}Presenter(new ${componentName}Model(), this, this);
    }

    @Override
    public void outDestroy() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(Throwable e) {

    }
}