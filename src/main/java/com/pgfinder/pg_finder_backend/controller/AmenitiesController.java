package com.pgfinder.pg_finder_backend.controller;

public class AmenitiesController {
}


//API	Method	Role	Functionality
///api/amenities	GET	ALL	Master list
///api/pgs/{pgId}/amenities	PUT	OWNER	Assign amenities
///api/pgs/{pgId}/rules	PUT	OWNER	Set rules




//
//🔍 SEARCH & DISCOVERY (HIGH VALUE)
//API	Method	Role	Functionality
///api/pgs/search	GET	ALL	Filtered search
///api/pgs/nearby	GET	ALL	Nearby PGs
///api/pgs/recommended	GET	ALL	Basic recommendations
//        Filters
//city, area, minRent, maxRent,
//gender, sharing, amenities,
//availability, rating
//📅 BOOKING & AVAILABILITY
//API	Method	Role	Functionality
///api/pgs/{pgId}/availability	GET	ALL	Check availability
///api/bookings	POST	USER	Create booking
///api/bookings/{id}	GET	USER	Booking details
///api/bookings/{id}/cancel	PATCH	USER	Cancel booking
//💰 PAYMENTS (SIMULATED)
//API	Method	Role	Functionality
///api/payments/initiate	POST	USER	Start payment
///api/payments/verify	POST	USER	Verify payment
///api/payments/{id}	GET	USER	Payment status
//⭐ REVIEWS & RATINGS
//API	Method	Role	Functionality
///api/pgs/{pgId}/reviews	POST	USER	Add review
///api/pgs/{pgId}/reviews	GET	ALL	View reviews
///api/reviews/{id}	DELETE	ADMIN	Remove review
//📊 OWNER DASHBOARD (READ-ONLY)
//API	Method	Role	Functionality
///api/owners/dashboard	GET	OWNER	Bookings, revenue
///api/owners/pgs/{pgId}/stats	GET	OWNER	PG stats
//🛡️ ADMIN DASHBOARD
//API	Method	Role	Functionality
///api/admin/dashboard	GET	ADMIN	Platform metrics
///api/admin/pgs	GET	ADMIN	Moderate PGs
///api/admin/bookings	GET	ADMIN	Booking oversight
//
//
//
