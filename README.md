# 使用PrefereceFragment实现设置页面

## PrefereceFragment简述

当我们在开发Android项目时一般都会用到首选项设置，在Android3.0之前我们都使用PreferenceActivity类来进行操作，这些设置习惯会被Preference自动保存下来，以便下次进入后恢复上一次的选择状态。

PreferenceActivity和普通的Activity不同，他们不使用setContentView加载布局，而是加载选项设置布局，而选择设置布局是以PreferenceScreen作为根元素的xml布局文件。

但在Android3.0之后Google不推荐我们使用PreferenceActivity来进行首选项设置，而是替换成了PreferenceFragment，其实二者的用法类似，只不过PreferenceFragment更容易扩展，所以推荐使用PreferenceFragment进行首选项设置。

PreferenceFragment是直接继承自Fragment，另选项布局xml文件的根节点一定是<PreferenceScreen>，在这个元素中我们可以添加不同的Preference，常用的Preference控件如下：

|     Preference     |   View   |  控件含义  |
| :----------------: | :------: | :--------: |
|     Preference     | TextView |   文本框   |
| CheckBoxPreference | CheckBox |   单选框   |
| EditTextPreference | EditText | 输入文本框 |
|   ListPreference   | ListView |   列表框   |
<div>

**所要用到的组合控件**

|        控件        |                    说明                    |
| :----------------: | :----------------------------------------: |
| PreferenceCategory | 用于组合一组Preference，使布局更具备层次感 |
|  PreferenceScreen  |        所有Preference元素的根节点。        |

**通用的属性**

```xml
android:key：每个Preference控件独一无二的”ID”,唯一表示此Preference。

android:defaultValue：默认值。例如，CheckPreference的默认值可为”true”，默认为选中状态；

android:enabled ： 表示该Preference是否可用状态。

android:title ：每个Preference在PreferenceScreen布局上显示的标题——大标题

android:summary ：每个Preference在PreferenceScreen布局上显示的标题——小标题(可以没有)

android:persistent：表示Preference元素所对应的值是否写入sharedPreferen文件中，如果是true，则表示写入；否则，则表示不写入该Preference元素的值。

android:dependency：表示一个Preference(用A表示)的可用状态依赖另外一个Preference(用B表示)。B可用，则A可用；B不可用，则A不可用。

android:disableDependentsState：与android:dependency相反。B可用，则A不可用；B不可用，则A可用。
```



## 具体使用实例

### 1.PreferenceFragment的布局文件

```xml
<!--此文件为设置加载的布局文件-->
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <PreferenceCategory android:title="In-line preferences">
    <CheckBoxPreference
        android:key="checkbox_preference"
        android:summary="This a checkbox"
        android:title="Checkbox_preference"/>
    </PreferenceCategory>
    <PreferenceCategory android:title="Dialog-based preferences">
        <EditTextPreference
            android:dialogTitle="Enter your favorite animal"
            android:title="EditTextPreference"
            android:key="edittext_preference"
            android:summary="An example that uses an edit text dialog"/>
        <ListPreference
            android:entries="@array/options"
            android:entryValues="@array/options"
            android:dialogTitle="Choose one"
            android:summary="An example that use a list dialog"
            android:title="List prefernce"
            android:key="list_preference"/>
        <!--需要补充列表项的数据来源-->
    </PreferenceCategory>
    <PreferenceCategory android:title="Launch preferences">
        <PreferenceScreen
            android:key="screen_preference"
            android:summary="Shows another screen of preferences"
            android:title="Screen preference">
            <CheckBoxPreference
                android:key="next_scrren_checkbox_preference"
                android:summary="Preference that is on the next screen but same hierarchy"
                android:title="Toggle preference"/>
        </PreferenceScreen>
        <PreferenceScreen
            android:summary="Launches an Activity from an intent"
            android:title="Intent preference"/>
            <intent
                android:action="android.intent.action.VIEW"
                android:data="http://www.google.com"/>
    </PreferenceCategory>
    <!--设置项关联，选中父选项时，子选项才显示。使用dependency属性-->
    <PreferenceCategory android:title="Preference attributes">
        <CheckBoxPreference
            android:key="parent_checkbox_preference"
            android:summary="This is visually parent"
            android:title="Parent checkbox preference" />
        <!-- 子类的可见类型是由样式属性定义的 -->
        <CheckBoxPreference
            android:dependency="parent_checkbox_preference"
            android:key="child_checkbox_preference"
            android:summary="This is visually a child"
            android:title="Child checkbox preference" />
    </PreferenceCategory>
</PreferenceScreen>
```

需要注意的一些属性



### 2.创建PreferenceFragment,并将布局文件加载进去

```java
package com.example.martinzou.android_exp_5;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by 10630 on 2018/5/25.
 */

public class PreFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从xml文件中加载选项
        addPreferencesFromResource(R.xml.preference);
    }

}
```

### 3.主活动设置

```java
package com.example.martinzou.android_exp_5;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载Fragment
        FragmentManager fragmentManager=getFragmentManager();
        //开启一个新事务
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        PreFragment preFragment=new PreFragment();
        transaction.add(R.id.layout1,preFragment);
        transaction.commit();
    }
}

```

### 4.0实现效果

<div align="center">
<img src="https://github.com/MartinZou0/Android_EXP_5/blob/master/shortcut/1.jpg" width="300"/><img src="https://github.com/MartinZou0/Android_EXP_5/blob/master/shortcut/2.jpg" width="300"  />
</div>
<div align="center">
<img src="https://github.com/MartinZou0/Android_EXP_5/blob/master/shortcut/3.jpg" width="300"/><img src="https://github.com/MartinZou0/Android_EXP_5/blob/master/shortcut/4.jpg" width="300"  />
</div>
