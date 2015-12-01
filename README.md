# NightOwl
![Logo](https://raw.githubusercontent.com/ashqal/NightOwl/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)
</br>
It is an experimental project for switching day/night mode on Android. 
View the [demo video](https://youtu.be/TV2_zAm446Q) on Youtube. This will help you understand the project.

## Dependencies
add nightowllib library to your project which published in jCenter
```java
compile 'com.asha.nightowllib:nightowllib:0.2'
```

## Setup
init the NightOwl in Application class, such as
```java
 NightOwl.builder().defaultMode(0).create();
```
then you need call three method in your Activity class, such as
```java
public class DemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // step1 before super.onCreate
        NightOwl.owlBeforeCreate(this);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        
        // step2 after setContentView
        NightOwl.owlAfterCreate(this);
        
        // write your code
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        // step3 onResume
        NightOwl.owlResume(this);
    }
    
}
```

switch skin everywhere as you like
```java
View v = findViewById(R.id.button);
v.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        NightOwl.owlNewDress(SettingActivity.this);
    }
});
```
and enjoy it.

##Usage
###General component
For example, if you want to change text color, you may 
**via Attribute**
```java
// layout.xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/large_text"
    android:textColor="#000000"
    night:night_textColor="#999999"
    />
```
**via Style**
```java
// layout.xml
<TextView
    android:id="@+id/title"
    android:style="@style/MyTextStyle"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
```java
// style.xml
<style name="MyTextStyle">
    <item name="android:textColor">#002fff</item>
    <item name="night_textColor">#fc575d</item>
    <item name="android:background">#fdceb7</item>
    <item name="night_background">#3d1416</item>
</style>
```
**via Text Appearance**
```java
// layout.xml
<TextView
    android:textAppearance="@style/TextPrimary"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
```java
// style.xml
<style name="TextPrimary" parent="TextAppearance.AppCompat.Title">
    <item name="android:textColor">#FF000000</item>
    <item name="night_textColor">#4d4d4d</item>
</style>
```

###Custom View
```java
public class CustomView extends View implements IOwlObserver {
  @Override
    public void onSkinChange(int mode, Activity activity) {
        int color = mode == 0 ? mColor : mColorNight;
        mPaint.setColor(color);
        this.invalidate();
    }
}
```
than register to NightOwl
```java
CustomView customView = new CustomView(this);
// register
NightOwl.owlRegisterCustom(customView);
```

##Supported Attributes
**View Table**
Name              | Attr
------------------| --------------------
View              | night_background
View              | night_alpha

**TextView Table extends View Table**
Name              | Attr
------------------| --------------------
TextView          | night_textColor
TextView          | night_textColorHint

**ImageView Table extends TextView Table**
Name              | Attr
------------------| --------------------
ImageView         | night_src

**ListView Table extends View Table**
Name              | Attr
------------------| --------------------
ListView          | night_listSelector
ListView          | night_divider

**Theme Table**
Name           | Attr
---------------| -------------------------
Theme          | night_colorPrimary
Theme          | night_colorPrimaryDark
Theme          | night_colorAccent
Theme          | night_navigationBarColor
Theme          | night_statusBarColor



##Memory leak?
Dont worry, there is no any Context or View instance saved in NightOwl, this project just insert some data object into a target view via
```
view.setTag(int key, Object value);
```

##LICENSE
```
Copyright 2015 Asha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
