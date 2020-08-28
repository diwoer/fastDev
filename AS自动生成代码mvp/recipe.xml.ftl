<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <#include "recipe_manifest.xml.ftl" />
    <@kt.addAllKotlinDependencies />

    //activity布局
	<instantiate from="root/res/layout/activity_main.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />			   
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />

    //activity
	<instantiate from="root/src/app_package/MainActivity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/view/activity/${componentName}Activity.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/view/activity/${activityClass}.${ktOrJavaExt}" />
		
    //contract		
	<instantiate from="root/src/app_package/MainContract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/contract/${componentName}Contract.${ktOrJavaExt}" />
	
	//model	
	<instantiate from="root/src/app_package/MainModel.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/model/${componentName}Model.${ktOrJavaExt}" />
	
	//presenter
	<instantiate from="root/src/app_package/MainPresenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/presenter/${componentName}Presenter.${ktOrJavaExt}" />
				   
</recipe>
