# JetPack之Room数据库

[TOC]

## 一、理论

### 1.1 `Room`数据库

`Room`持久性库是在`SQLite`的基础上提供了一个抽象层，让用户能够在充分利用`SQLite`的强大功能的同时，获享更强健的数据库访问机制。

该库可帮助您在运行应用的设备上创建应用数据的缓存，此缓存充当应用的单一可信来源，使用户能够在应用中查看关键信息的一致副本，无论用户是否联网。

参考：[https://developer.android.google.cn/topic/libraries/architecture/room](https://developer.android.google.cn/topic/libraries/architecture/room)

### 1.2 三个重要对象

* `Entity`：`Student`
* `Dao(Database Access Object`)：StudentDao
* `Database`：StudentDatabase

## 二、实践

### 2.1 简单使用

#### 2.1.1 添加依赖

在`.gradle`文件中添加依赖：

```groovy
dependencies {
		/...
    //加入Room的支持
    def room_version = "2.3.0-alpha03"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
}
```

#### 2.1.2 实体`Student`

```java
@Entity
public class Student {
    //主键，自动增长
    @PrimaryKey(autoGenerate = true)
    private int id;

    //如果写了ColumnInfo注解，则使用注解中的名称作为数据库列名
    @ColumnInfo(name = "name")
    private String name; //默认使用name

    @ColumnInfo(name = "age")
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
  
		//... getter() setter()
}
```

#### 2.1.3 数据访问对象`StudentDao`

```java
@Dao //Database Access Object
public interface StudentDao {
    //增删改查
    @Insert  //内部会自动生成代码
    void insertStudents(Student... students);

    @Update
    void updateStudents(Student... students);

    @Delete
    void deleteStudents(Student... students);

    //删除全部 没有条件
    @Query("DELETE FROM student") //内部默认转成大写
    void deleteAllStudents();

    //查询全部
    @Query("SELECT * FROM student ORDER BY ID DESC")
    //List<Student> getAllStudents();
    LiveData<List<Student>> getAllStudentLiveData();
}
```

#### 2.1.4 数据库实例`StudentDatabase`

```java
@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {

    //create database 很耗费性能，整个项目一个实例（单例模式）
    private static volatile StudentDatabase studentDatabase;

    public static StudentDatabase getInstance(Context context) {
        if(null == studentDatabase) {
            synchronized (StudentDatabase.class) {
                if(null == studentDatabase) {
                    //默认只能在异步线程中运行，如果在主线程执行就会报错
                    studentDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                StudentDatabase.class, "student_database")
                            .allowMainThreadQueries() //强制允许在main线程执行
                            .build();
                }
            }
        }
        return studentDatabase;
    }

    //注意：如果有多个Entity，就需要写多个Dao
    public abstract StudentDao getStudentDao();
}
```

#### 2.1.5 增删改查

```java
public class MainActivity extends AppCompatActivity {
		//...控件声明

    private StudentDatabase studentDatabase;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListeners();
    }

    private void initView() {
				//... findViewById()

        studentDatabase = StudentDatabase.getInstance(this);
        studentDao = studentDatabase.getStudentDao();

        //观察数据的变化
        studentDao.getAllStudentLiveData().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                StringBuilder sb = new StringBuilder();
                for (Student student : students) {
                    sb.append(student.getId())
                            .append(":")
                            .append(student.getName())
                            .append("==")
                            .append(student.getAge())
                            .append("\n");
                }
                tvInfo.setText(sb.toString());
            }
        });
    }

    private void setListeners() {
        //插入
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student1 = new Student("张三", 20);
                Student student2 = new Student("李四", 30);
                studentDao.insertStudents(student1, student2);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student("AAA", 1233);
                student.setId(2); //更新Id 为2的这条数据
                studentDao.updateStudents(student);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentDao.deleteAllStudents();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setId(1); //删除ID为1的记录
                studentDao.deleteStudents(student);
            }
        });
    }
}
```



