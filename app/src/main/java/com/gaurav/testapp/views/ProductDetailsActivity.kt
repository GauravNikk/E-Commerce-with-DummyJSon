package com.gaurav.testapp.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gaurav.testapp.R
import com.gaurav.testapp.adapter.ReviewAdapter
import com.gaurav.testapp.apiservices.RetrofitClient
import com.gaurav.testapp.model.Product
import com.gaurav.testapp.model.ProductDetail
import com.gaurav.testapp.model.Review
import com.gaurav.testapp.util.Dlghelper.Companion.showmessg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsActivity : AppCompatActivity() {
    var isExapanded=false
    private lateinit var titleTextTv: TextView
    private lateinit var product:Product
    private lateinit var productDetail:ProductDetail

    private lateinit var productImage: ImageView
    private lateinit var ivAction: ImageView
    private lateinit var productTitle: TextView
    private lateinit var productPrice: TextView
    private lateinit var productDescription: TextView
    private lateinit var showMoreButton: TextView
    private lateinit var productQuantity: TextView
    private lateinit var decrementButton: TextView
    private lateinit var incrementButton: TextView
    private lateinit var addToCartButton: TextView
    private lateinit var buyNowButton: TextView
    private lateinit var reviewContainer: LinearLayout
    private lateinit var reviewRecyclerView: RecyclerView

    private var quantity: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivAction = findViewById(R.id.ivAction)
        titleTextTv = findViewById(R.id.titleTv)
        productImage = findViewById(R.id.productImage)
        productTitle = findViewById(R.id.productTitle)
        productPrice = findViewById(R.id.productPrice)
        productDescription = findViewById(R.id.productDescription)
        productQuantity = findViewById(R.id.productQuantity)
        decrementButton = findViewById(R.id.decrementButton)
        incrementButton = findViewById(R.id.incrementButton)
        addToCartButton = findViewById(R.id.addToCartButton)
        buyNowButton = findViewById(R.id.buyNowButton)
        showMoreButton = findViewById(R.id.showMoreButton)
        reviewContainer = findViewById(R.id.reviewContainer)
         reviewRecyclerView = findViewById(R.id.rvRecyclerView)


        product = intent.getParcelableExtra("product")!!


        decrementButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                productQuantity.text = quantity.toString()
            }
        }

        incrementButton.setOnClickListener {
            quantity++
            productQuantity.text = quantity.toString()
        }
        ivAction.setOnClickListener {
            finish()
        }



        addToCartButton.setOnClickListener {
            showmessg(this@ProductDetailsActivity,"Success","${productDetail.title} added in your cart.")
        }

        buyNowButton.setOnClickListener {
            showmessg(this@ProductDetailsActivity,"Success","${productDetail.title} buy now.")
        }


        Log.d("TAG", "onCreate: $product")
        fetchProductsDetails(product.id)
        titleTextTv.text=product.title
    }

    private fun fetchProductsDetails(productId: Int) {
        val call = RetrofitClient.apiService.getProductDetails(productId)
        call.enqueue(object : Callback<ProductDetail> {
            override fun onResponse(call: Call<ProductDetail>, response: Response<ProductDetail>) {
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse != null) {
                        productDetail = productResponse
                        Log.d("TAG", "onResponse:::::: $productDetail")
                        setupUi();
                    } else {
                        showmessg(this@ProductDetailsActivity, "Error", "No product details found")
                    }
                } else {
                    showmessg(this@ProductDetailsActivity, "Error", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProductDetail>, t: Throwable) {
                showmessg(this@ProductDetailsActivity, "Error", t.localizedMessage)
            }
        })
    }

    private fun setupUi() {

        productDetail.let { product ->
            productTitle.text = product.title
            productPrice.text = "$${product.price}"


            productImage.load(product.thumbnail) {
                crossfade(true)
                placeholder(R.drawable.placeholder_image)
            }


            var productDescriptionn = product.description +" This is a wonderful product that has amazing features. You can use it for multiple purposes. It's crafted with high-quality materials and provides exceptional performance. Don't miss out on this incredible deal!"

            productDescription.text = productDescriptionn

            val lineCount = productDescription.lineCount
            print("++++++++++++++++++++++++++++++  $lineCount")
            if (lineCount > 3) {
                showMoreButton.visibility = View.VISIBLE
            } else {
                showMoreButton.visibility = View.GONE
            }

            setupProductDescriptionControl()
            loadReviews(product.reviews)
        }

    }

    private fun loadReviews(reviews: List<Review>) {
        reviewRecyclerView.layoutManager = LinearLayoutManager(this)
        reviewRecyclerView.adapter = ReviewAdapter(reviews)
    }
    private fun setupProductDescriptionControl() {
        //this one for expand description
        showMoreButton.visibility=View.VISIBLE

        isExapanded = false
        showMoreButton.setOnClickListener {
            if (isExapanded) {
                productDescription.maxLines = 3
                showMoreButton.text = " More..."
            } else {
                productDescription.maxLines = Int.MAX_VALUE
                showMoreButton.text = "...Less"
            }
            isExapanded = !isExapanded
        }
    }




    //        reviewContainer.removeAllViews()
//        for (review in reviews) {
//            val reviewView = LayoutInflater.from(this).inflate(R.layout.item_review, reviewContainer, false)
//            val reviewerName = reviewView.findViewById<TextView>(R.id.reviewerName)
//            val reviewComment = reviewView.findViewById<TextView>(R.id.reviewComment)
//            val reviewRating = reviewView.findViewById<TextView>(R.id.reviewRating)
//
//            reviewerName.text = review.reviewerName
//            reviewComment.text = review.comment
//            reviewRating.text = "Rating: ${review.rating}"
//
//            reviewContainer.addView(reviewView)
//        }

}