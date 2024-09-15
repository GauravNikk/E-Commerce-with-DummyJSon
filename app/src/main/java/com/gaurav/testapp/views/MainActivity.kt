package com.gaurav.testapp.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.gaurav.testapp.CategoryAdapter
import com.gaurav.testapp.ProductAdapter
import com.gaurav.testapp.R

import com.gaurav.testapp.apiservices.RetrofitClient
import com.gaurav.testapp.model.Category
import com.gaurav.testapp.model.ProductResponse
import com.gaurav.testapp.util.Dlghelper
import com.gaurav.testapp.util.Dlghelper.Companion.showmessg
import gaurav.iosdialog.IOSDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var limitCount=20
    var items=0

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var productRecyclerView: RecyclerView
    private var selectedCategory: String? = null
    private lateinit var searchEditText: EditText
    private lateinit var ivSearch: ImageView
    private lateinit var itemCount: TextView
    private lateinit var shimmerLayoutProduct: ShimmerFrameLayout
    private lateinit var shimmerLayoutCate: ShimmerFrameLayout

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        productRecyclerView = findViewById(R.id.productRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        ivSearch = findViewById(R.id.ivSearch)
        itemCount = findViewById(R.id.itemCount)

        shimmerLayoutProduct = findViewById(R.id.shimmerLayoutProduct)
        shimmerLayoutCate = findViewById(R.id.shimmerLayoutCate)



        itemTVUpdate()
        ivSearch.setOnClickListener {
            if (searchEditText.text.toString().trim().length>=3){
                performSearch(searchEditText.text.toString())
            }else{
                showmessg(this@MainActivity,"Alert","Please enter at least 3 characters to search")
            }
        }

        setupSearch()

        fetchCategories()
        fetchAllProducts()

        productAdapter = ProductAdapter(emptyList())
        productRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        productRecyclerView.adapter = productAdapter
    }

    private fun itemTVUpdate() {
        itemCount.text="$items item found"
    }

    private fun fetchCategories() {
        shimmerLayoutCate.startShimmer()
        shimmerLayoutCate.visibility=View.VISIBLE

        val call = RetrofitClient.apiService.getCategories()
        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                shimmerLayoutCate.stopShimmer()
                shimmerLayoutCate.visibility = View.GONE
                val categories = response.body() ?: emptyList()
                val allCategory = Category(slug = "all", name = "All ", url = "")
                val categoriesWithAll = mutableListOf(allCategory)
                categoriesWithAll.addAll(categories)

                categoryRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                categoryRecyclerView.adapter = CategoryAdapter(categoriesWithAll) { category ->
                    selectedCategory = category.slug
                    fetchProducts()
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                shimmerLayoutCate.stopShimmer()
                shimmerLayoutCate.visibility = View.GONE
                showmessg(this@MainActivity,"Error", t.localizedMessage)
            }
        })
    }

    private fun fetchProducts() {
        shimmerLayoutProduct.startShimmer()
        shimmerLayoutProduct.visibility=View.VISIBLE

        limitCount=20;
        Log.d("TAG", "fetchProducts: "+selectedCategory)
        productAdapter = ProductAdapter(emptyList())
        productRecyclerView.adapter = productAdapter

        if (selectedCategory.isNullOrEmpty()) {
            fetchAllProducts()
        } else if (selectedCategory.equals("all")) {
            limitCount=200;
            fetchAllProducts()
        } else {
            fetchProductsByCategory(selectedCategory!!)
        }
    }

    private fun fetchAllProducts() {
        val call = RetrofitClient.apiService.getProducts(limitCount)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    shimmerLayoutProduct.stopShimmer()
                    shimmerLayoutProduct.visibility = View.GONE
                    val products = response.body()?.products ?: emptyList()
                    items=products.size
                    itemTVUpdate()
                    productAdapter = ProductAdapter(products)
                    productRecyclerView.adapter = productAdapter
                } else {
                    shimmerLayoutProduct.stopShimmer()
                    shimmerLayoutProduct.visibility = View.GONE
                    showmessg(this@MainActivity,"Error", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                shimmerLayoutProduct.stopShimmer()
                shimmerLayoutProduct.visibility = View.GONE
                showmessg(this@MainActivity,"Error", t.localizedMessage)
            }
        })
    }

    private fun fetchProductsByCategory(category: String) {
        val call = RetrofitClient.apiService.getProductsByCategory(category, limitCount)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    shimmerLayoutProduct.stopShimmer()
                    shimmerLayoutProduct.visibility = View.GONE
                    val products = response.body()?.products ?: emptyList()
                    items=products.size
                    itemTVUpdate()
                    productAdapter = ProductAdapter(products)
                    productRecyclerView.adapter = productAdapter
                } else {
                    shimmerLayoutProduct.stopShimmer()
                    shimmerLayoutProduct.visibility = View.GONE
                    showmessg(this@MainActivity,"Error", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                shimmerLayoutProduct.stopShimmer()
                shimmerLayoutProduct.visibility = View.GONE
                showmessg(this@MainActivity,"Error", t.localizedMessage)
            }
        })
    }

   


    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count>=3){
                    performSearch(s.toString())
                }
            }
            override fun afterTextChanged(s: Editable?) {
//                searchEditText.setOnEditorActionListener { v, actionId, _ ->
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (s.toString().trim().length>=3){
                    performSearch(s.toString())
                }
//                        true
//                    } else {
//                        false
//                    }
//                }
            }
        })
    }

    private fun performSearch(query: String) {
        if (query.length < 3) {
//            showmessg("Alert","Please enter at least 3 characters to search")
            return
        }
        productAdapter = ProductAdapter(emptyList())
        productRecyclerView.adapter = productAdapter
        val call = RetrofitClient.apiService.searchProducts(query, limitCount)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    shimmerLayoutProduct.stopShimmer()
                    shimmerLayoutProduct.visibility = View.GONE
                    val products = response.body()?.products ?: emptyList()
                    items=products.size
                    itemTVUpdate()
                    productAdapter = ProductAdapter(products)
                    productRecyclerView.adapter = productAdapter
                } else {
                    shimmerLayoutProduct.stopShimmer()
                    shimmerLayoutProduct.visibility = View.GONE
                    showmessg(this@MainActivity,"Error", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                shimmerLayoutProduct.stopShimmer()
                shimmerLayoutProduct.visibility = View.GONE
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

}

