package com.studio.neopanda.healthinbox.database

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.studio.neopanda.healthinbox.database.dao.AlimentDao
import com.studio.neopanda.healthinbox.database.dao.MealDao
import com.studio.neopanda.healthinbox.database.dao.RecipeDao
import com.studio.neopanda.healthinbox.database.dao.WeightDao
import com.studio.neopanda.healthinbox.database.entities.Aliment
import com.studio.neopanda.healthinbox.database.entities.Meal
import com.studio.neopanda.healthinbox.database.entities.Recipe
import com.studio.neopanda.healthinbox.database.entities.Weight


@Database(
    entities = [Aliment::class, Recipe::class, Meal::class, Weight::class],
    version = 4,
    exportSchema = false
)
abstract class AlimentDatabase : RoomDatabase() {

    abstract fun alimentDao(): AlimentDao
    abstract fun recipeDao(): RecipeDao
    abstract fun mealDao(): MealDao
    abstract fun weightDao(): WeightDao


    companion object {
        private var instance: AlimentDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AlimentDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlimentDatabase::class.java, "aliments_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }

            return instance as AlimentDatabase
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                Log.e("DBMANAGEMENT", "DATABASE ONCREATE")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                PopulateDBAsyncTask(
                    instance!!
                ).execute()
                Log.e("DBMANAGEMENT", "DATABASE ONOPEN")
            }
        }

        private class PopulateDBAsyncTask constructor(db: AlimentDatabase) :
            AsyncTask<Void, Void, Void>() {

            private val alimentDao: AlimentDao = db.alimentDao()
            private val mealDao: MealDao = db.mealDao()
            private val recipeDao: RecipeDao = db.recipeDao()
            private val weightDao: WeightDao = db.weightDao()

            override fun doInBackground(vararg voids: Void): Void? {
                alimentDao.insert(
                    Aliment(
                        "Artichoke",
                        60,
                        128
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Asparagus spear",
                        2,
                        12
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Aubergine",
                        115,
                        458
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Olive (Black)",
                        2,
                        3
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Broccoli bunch",
                        207,
                        608
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cabbage head",
                        227,
                        908
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Carrot",
                        25,
                        61
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Celery stalk",
                        6,
                        40
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cherry Tomato",
                        20,
                        20
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Corn cup",
                        562,
                        154
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Courgette",
                        33,
                        196
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cream Spinach",
                        148,
                        200
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cucumber",
                        66,
                        410
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Eggplant",
                        115,
                        458
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Endive head",
                        87,
                        513
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Garlic clove",
                        4,
                        3
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Green Beans cup",
                        34,
                        110
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Olive (green)",
                        2,
                        3
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Green Onion",
                        5,
                        15
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Lettuce head",
                        90,
                        600
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Mushrooms",
                        1,
                        5
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pepper",
                        20,
                        75
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pumpkin",
                        51,
                        196
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Potato",
                        164,
                        213
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Radish",
                        1,
                        5
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Spinach bunch",
                        78,
                        340
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Sweet Potato",
                        112,
                        130
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tomato",
                        20,
                        111
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Zucchini",
                        33,
                        196
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "PineApple (medium)",
                        50,
                        112
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Banana (large)",
                        110,
                        126
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Apple (large)",
                        130,
                        142
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Avocado (medium)",
                        50,
                        30
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Grapefruit 1/2 grape",
                        52,
                        123
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Kiwi",
                        112,
                        183
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Mandarin",
                        48,
                        90
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Mango",
                        202,
                        336
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Watermelon wedge",
                        86,
                        286
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Lychee",
                        7,
                        10
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Absinthe 10cl",
                        348,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Beer 50cl",
                        215,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bourbon 10cl",
                        233,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Brandy 10cl",
                        213,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Canadian Whiskey 10cl",
                        216,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Champagne 50cl",
                        375,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cider 50cl",
                        245,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cognac 10cl",
                        235,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cointreau 10cl",
                        320,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gin 10cl",
                        263,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Glenfiddich 10cl",
                        230,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Grand Marnier 10cl",
                        253,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Irish Whiskey 10cl",
                        233,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Jack Daniel's 10cl",
                        146,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Jaggermeister 10cl",
                        250,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Jim Beam 10cl",
                        222,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Liquor 10cl",
                        250,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Punch 50cl",
                        310,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Red Wine 50cl",
                        420,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Prosecco 50cl",
                        330,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Rose Wine 50cl",
                        360,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Rum 10cl",
                        231,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Sangria 50cl",
                        480,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Scotch 10cl",
                        222,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tequila 10cl",
                        110,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Triple Sec 10cl",
                        153,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Vodka 10cl",
                        231,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "White Wine 50cl",
                        410,
                        500
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Apple Juice 10cl",
                        58,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Apricot Nectar 10cl",
                        56,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Banana Juice 10cl",
                        50,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Blackberry Juice 10cl",
                        48,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Capri-Sun 10cl",
                        41,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Carrot Juice 10cl",
                        40,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Coconut Milk 10cl",
                        233,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cranberry Juice 10cl",
                        46,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Energy Drink 10cl",
                        87,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Grape Juice 10cl",
                        60,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Lemon Juice 10cl",
                        21,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Lemonade 10cl",
                        50,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Nestea 10cl",
                        21,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Orange Juice 10cl",
                        46,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Peach Juice 10cl",
                        54,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pear Juice 10cl",
                        60,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pineapple Juice 10cl",
                        53,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pomegranate Juice 10cl",
                        66,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tomato Juice 10cl",
                        17,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "White-grape Juice 10cl",
                        75,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "American Cheese",
                        148,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Babybel",
                        333,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Blue Cheese",
                        353,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Brie",
                        334,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Camembert",
                        300,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cheddar",
                        403,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cheese Fondue",
                        228,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chester",
                        387,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cottage Cheese",
                        98,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Edam Cheese",
                        357,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Emmentaler",
                        357,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Feta",
                        264,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Mozzarella",
                        280,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Goat Cheese",
                        364,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gorgonzola",
                        350,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gouda",
                        356,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gruyere",
                        413,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Maasdam",
                        344,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Parmesan",
                        431,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Raclette Cheese",
                        357,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "String Cheese",
                        250,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cannelloni",
                        146,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Egg Noodles",
                        384,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cellophane Noodles",
                        351,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Farfalle",
                        358,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Fusilli",
                        352,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Low Carb Pasta",
                        282,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Macaroni",
                        370,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Penne",
                        351,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Ravioli",
                        77,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Soy Noodles",
                        216,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Spaghetti",
                        370,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tagliatelle",
                        370,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Vermicelli",
                        368,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Whole-Grain Noodles",
                        347,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Whole-Grain Spaghetti",
                        351,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "After Eight",
                        400,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Animal Crackers",
                        446,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Caramel Popcorn",
                        376,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Caramel squares",
                        367,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chocolate",
                        529,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chocolate Bar",
                        533,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chocolate Chips",
                        493,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cookies",
                        488,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cotton Candy",
                        643,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Ferrero Rocher",
                        576,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Granola Bars",
                        452,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gummi Bears",
                        316,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gumdrops",
                        321,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Jelly Beans",
                        354,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Jelly Belly",
                        354,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Kit Kat",
                        521,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Lindt Chocolate",
                        548,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Lollipop",
                        392,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "M&M's",
                        479,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Maltsers",
                        498,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Mars Bar",
                        448,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Marshmallows",
                        318,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Marzipan",
                        411,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Milky Way",
                        449,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Peanut Bar",
                        533,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pez",
                        427,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Popcorn",
                        358,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Roasted Almonds",
                        452,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Skittles",
                        405,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Smarties",
                        357,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Snickers",
                        484,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Speculoos",
                        469,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Toblerone",
                        525,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Twix",
                        495,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Waffles",
                        272,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Baked Ham",
                        343,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Beef Salami",
                        375,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Boiled Ham",
                        126,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bologna",
                        247,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chicken Breast Fillet",
                        79,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chopped Ham",
                        180,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chorizo",
                        455,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Corned Beef",
                        251,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Ham Sausage",
                        164,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Head Cheese",
                        157,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Parna Ham",
                        345,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pastrami",
                        133,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pepperoni",
                        494,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pork Roast",
                        247,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Prosciutto",
                        195,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Roast Beef",
                        267,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Salami",
                        336,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Smoked Ham",
                        107,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Smoked Turkey Breast",
                        104,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Turkey Breast",
                        104,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Turkey Ham",
                        126,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Turkey Salami",
                        152,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Andouille",
                        232,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bacon",
                        407,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Boudin",
                        194,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Breakfast Sausage",
                        339,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chicken Meat",
                        79,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chicken Salad",
                        81,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Frankfurt Sausage",
                        305,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Hot-Dog Sausage",
                        278,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pork",
                        247,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Smoked Sausage",
                        301,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "SevenUp 10cl",
                        44,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Canada Dry",
                        41,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cherry Coke",
                        44,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Coke",
                        42,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Dr.Pepper",
                        27,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Fanta",
                        39,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gatorade",
                        25,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Ice Tea",
                        27,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Kool-Aid",
                        26,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pepsi",
                        44,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Soda",
                        53,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Powerade",
                        120,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Sprite",
                        37,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tonic Water",
                        35,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Vanille Coke",
                        45,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bagel",
                        257,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Baguette",
                        274,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Banana Bread",
                        326,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Biscuit",
                        269,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bread Pudding",
                        153,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Breadsticks",
                        400,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Black Bread",
                        250,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Brioche",
                        346,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Brown Bread",
                        246,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Brownies",
                        405,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bun",
                        316,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cannoli",
                        254,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Caramel Cake",
                        385,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Ciabatta",
                        271,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cinnamon Bun",
                        436,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cornbread",
                        179,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Crepes",
                        224,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Croissant",
                        406,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cupcakes",
                        305,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Donut",
                        421,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Empanada",
                        335,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Focaccia",
                        249,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Garlic Bread",
                        350,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Hot-Dog Bun",
                        279,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Italian Bread",
                        271,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Muffin",
                        296,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Oatmeal Cookies",
                        450,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pan de Sal",
                        293,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pancake",
                        233,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Panettone",
                        318,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pie",
                        237,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pita Bread",
                        275,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Potato Bread",
                        266,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Pretzel",
                        338,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Puff Pastry",
                        558,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Raisin Bread",
                        274,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Roll",
                        316,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Rye Bread",
                        259,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Sandwich Bread",
                        251,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Scone",
                        362,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Soufflé",
                        204,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Toast",
                        261,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tortilla",
                        297,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "White Bread",
                        238,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Whole-Wheat Bread",
                        247,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Balsamic Vinaigrette 10cl",
                        290,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "BBQ Sauce",
                        150,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bechamel Sauce",
                        225,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Bearnaise Sauce",
                        414,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Blue Cheese Dressing 10cl",
                        533,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Caesar Dressing",
                        429,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chasseur Sauce",
                        45,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Chili Sauce",
                        180,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Cream Sauce",
                        180,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Curry Sauce",
                        26,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Gravy",
                        53,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Harissa",
                        52,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Ketchup",
                        100,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Mustard",
                        60,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Mustard Sauce",
                        645,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Sour Cream",
                        217,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Soy Sauce 10cl",
                        64,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tabasco",
                        70,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Teriyaki Sauce",
                        89,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tomato Sauce",
                        24,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Tzatziki",
                        94,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Vinaigrette 10cl",
                        120,
                        100
                    )
                )
                alimentDao.insert(
                    Aliment(
                        "Yogurt Dressing 10cl",
                        45,
                        100
                    )
                )

                mealDao.insert(
                    Meal(
                        "Fruit Salad",
                        "22-02-2020",
                        370
                    )
                )
                mealDao.insert(
                    Meal(
                        "Banana Apple",
                        "22-02-2020",
                        270
                    )
                )
                mealDao.insert(
                    Meal(
                        "Avocado PineApple",
                        "22-02-2020",
                        100
                    )
                )
                mealDao.insert(
                    Meal(
                        "Snacks",
                        "23-02-2020",
                        225
                    )
                )
                mealDao.insert(
                    Meal(
                        "Kebab",
                        "23-02-2020",
                        680
                    )
                )
                mealDao.insert(
                    Meal(
                        "Caesar salad",
                        "24-02-2020",
                        495
                    )
                )
                mealDao.insert(
                    Meal(
                        "Aperitive",
                        "24-02-2020",
                        350
                    )
                )
                mealDao.insert(
                    Meal(
                        "BBQ",
                        "25-02-2020",
                        545
                    )
                )
                mealDao.insert(
                    Meal(
                        "KFC",
                        "25-02-2020",
                        870
                    )
                )
                mealDao.insert(
                    Meal(
                        "Russian salad",
                        "26-02-2020",
                        375
                    )
                )

                recipeDao.insert(
                    Recipe(
                        "Couscous",
                        "- 3 Tomatoes\n- 3 Peppers\n- 1500g Couscous (thin grain)\n- 350g Chicken\n- 350g Beef\n- 350g Veal\n- 10g Paprika\n- 35g Harissa",
                        "1. Verser in the cocotte \n2. Make all to mijotte un moment\n3. Toss into the guests' assiettes\n4. Throw the sauce",
                        10,
                        2,
                        50,
                        200
                    )
                )
                recipeDao.insert(
                    Recipe(
                        "Indian Chicken",
                        "- 350g Breast Chicken\n- 10g Curry\n- Salt\n- Pepper\n- 5g Paprika",
                        "1. Cut the chicken in 3x3cm dices.\n2. Put the chicken dices inside a bowl and add the salt, pepper, paprika, curry and mix them up.\n3. Fry the content in a pan for 5 mn (fast cooking).\n4. Serve hot, sexy chicken on da table.",
                        4,
                        1,
                        8,
                        10
                    )
                )
                recipeDao.insert(
                    Recipe(
                        "The Gravedigger's cocktail",
                        "- 2cl Whiskey\n- 2cl Vodka\n- 2cl Pastis\n- 2 cl Rum\n- 2cl Gin\n- 2 cubes of ice",
                        "1. Mix all the alcools into a glass.\n2. Add the ice.\n3. Drink and hope not to die.",
                        1,
                        1,
                        5,
                        1
                    )
                )

                weightDao.insert(
                    Weight(98, "01-03-2020")
                )
                weightDao.insert(
                    Weight(95, "02-03-2020")
                )
                weightDao.insert(
                    Weight(93, "03-03-2020")
                )
                weightDao.insert(
                    Weight(92, "04-03-2020")
                )

                return null
            }

            override fun onProgressUpdate(vararg values: Void?) {
                super.onProgressUpdate(*values)

            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
            }
        }
    }
}