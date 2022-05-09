package com.example.e_auction.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.ToolbarWidgetWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.e_auction.R
import com.example.e_auction.application.MyApplication
import com.example.e_auction.fragment.MyProductsFragment
import com.example.e_auction.fragment.OfferedProductsFragment
import com.example.e_auction.list_view.Product
import com.example.e_auction.session.SessionManager
import com.google.android.material.tabs.TabLayout

import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var sessionManger:SessionManager
    lateinit var searchView:SearchView
    lateinit var myProductsFragment:MyProductsFragment
    lateinit var offeredProductsFragment:OfferedProductsFragment

    private val tabIcons = intArrayOf(0, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mToolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        sessionManger=MyApplication.getInstance().getSession()
        sessionManger.ifUserLoggedOut(this)

        setupViewPager()

        val tabLayout:TabLayout =findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewpager)

        fabAddProduct.setOnClickListener {
            val i=Intent(this@MainActivity,AddProductActivity::class.java)
            startActivity(i)
        }
//        setupTabIcons(tabLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)

        val searchItem:MenuItem=menu!!.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }
        })

       searchView = searchItem.actionView as SearchView
        setupSearchView()

        return super.onCreateOptionsMenu(menu)
    }

    fun setupSearchView(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val searchInput=newText!!.toLowerCase()
                val filterListProductsOffered:ArrayList<Product> = ArrayList()
                val filterListProductsMine:ArrayList<Product> = ArrayList()
                for( product: Product in offeredProductsFragment.listProducts){
                    if(product.ProductName.contains(searchInput))
                        filterListProductsOffered.add(product)
                }
                offeredProductsFragment.mAdapter.updateList(filterListProductsOffered)

                for( product: Product in myProductsFragment.listProducts){
                    if(product.ProductName.contains(searchInput))
                        filterListProductsMine.add(product)
                }
                myProductsFragment.mAdapter.updateList(filterListProductsMine)

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {



                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout->{
                sessionManger.logout(this)
            }
        }
        return true
    }

    private fun setupTabIcons(tabLayout: TabLayout) {

        tabLayout.getTabAt(0)!!.setIcon(tabIcons[0])

        tabLayout.getTabAt(1)!!.setIcon(tabIcons[1])
    }

    private fun setupViewPager() {
        val adapter = ViewPgerAdapter(supportFragmentManager)


//        adapter.addFragment(MyProductsFragment.newInstance("","",searchView), resources.getString(R.string.str_my_products))
//
//        adapter.addFragment(OfferedProductsFragment.newInstance("","",searchView), resources.getString(R.string.str_offered_products))

        myProductsFragment=MyProductsFragment.newInstance("","")
        adapter.addFragment(myProductsFragment, resources.getString(R.string.str_my_products))
        offeredProductsFragment=OfferedProductsFragment.newInstance("","")
        adapter.addFragment(offeredProductsFragment, resources.getString(R.string.str_offered_products))


        viewpager.adapter=adapter
    }

    internal inner class ViewPgerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }
    }
}
