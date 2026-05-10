import React, { useEffect, useState } from 'react';
import { pgService } from '../services/api';
import PgCard from '../components/PgCard';
import { Search, MapPin, Sparkles, Filter } from 'lucide-react';

const HomePage: React.FC = () => {
  const [pgs, setPgs] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPgs = async () => {
      try {
        const response = await pgService.getAllPgs();
        setPgs(response.data.data);
      } catch (error) {
        console.error('Error fetching PGs:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchPgs();
  }, []);

  return (
    <div className="pt-24 pb-12 bg-gradient min-h-screen">
      {/* Hero Section */}
      <section className="container mb-24 text-center relative">
        {/* Background Glows */}
        <div className="absolute -top-24 left-1/2 -translate-x-1/2 w-full max-w-4xl h-96 bg-primary/20 blur-[120px] -z-10 rounded-full"></div>
        
        <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full glass border-primary/20 mb-8 animate-fade-in">
          <Sparkles size={14} className="text-primary" />
          <span className="text-xs font-semibold uppercase tracking-wider text-primary">Discover your space</span>
        </div>

        <h1 className="text-5xl md:text-7xl font-bold mb-8 animate-fade-in delay-100 tracking-tight">
          Find Your Perfect <br/>
          <span className="text-primary text-gradient">Home Away From Home</span>
        </h1>
        
        <p className="text-xl text-text-muted mb-12 max-w-2xl mx-auto animate-fade-in delay-200 leading-relaxed">
          Explore premium PG accommodations with verified owners, top-notch amenities, and a community that feels like family.
        </p>

        {/* Search Bar */}
        <div className="max-w-4xl mx-auto glass p-3 rounded-2xl flex flex-col md:flex-row items-center gap-3 animate-fade-in delay-300 shadow-2xl">
          <div className="flex-1 flex items-center gap-3 px-5 py-4 w-full">
            <Search className="text-primary" size={20} />
            <input 
              type="text" 
              placeholder="Search by city, area or PG name..." 
              className="bg-transparent border-none w-full text-text focus:ring-0 placeholder:text-text-muted/50 font-medium"
            />
          </div>
          <div className="h-10 w-px bg-border hidden md:block opacity-50"></div>
          <div className="flex-1 hidden md:flex items-center gap-3 px-5 py-4 w-full">
            <MapPin className="text-secondary" size={20} />
            <select className="bg-transparent border-none w-full text-text focus:ring-0 font-medium cursor-pointer">
              <option value="">All Cities</option>
              <option value="bangalore">Bangalore</option>
              <option value="mumbai">Mumbai</option>
              <option value="delhi">Delhi</option>
            </select>
          </div>
          <button className="btn btn-primary px-10 py-4 w-full md:w-auto text-lg shadow-primary/20">
            Search Rooms
          </button>
        </div>
      </section>

      {/* Featured PGs */}
      <section className="container">
        <div className="flex items-center justify-between mb-12 animate-fade-in delay-300">
          <div>
            <h2 className="text-4xl font-bold mb-2">Featured Listings</h2>
            <p className="text-text-muted">Handpicked premium accommodations for you</p>
          </div>
          <button className="btn btn-outline">
            <Filter size={18} />
            <span>Filters</span>
          </button>
        </div>

        {loading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
            {[1, 2, 3, 4, 5, 6].map((n) => (
              <div key={n} className="bg-surface/50 h-[450px] rounded-3xl animate-pulse border border-border"></div>
            ))}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10 animate-fade-in delay-300">
            {pgs.map((pg) => (
              <PgCard key={pg.id} {...pg} />
            ))}
            {pgs.length === 0 && (
              <div className="col-span-full text-center py-32 glass rounded-3xl border-dashed border-primary/20">
                <div className="w-20 h-20 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-6">
                  <Search size={32} className="text-primary" />
                </div>
                <h3 className="text-2xl font-bold mb-2">No PGs found</h3>
                <p className="text-text-muted mb-8">Try adjusting your search criteria or explore other cities.</p>
                <button className="btn btn-primary" onClick={() => window.location.reload()}>Browse All Listings</button>
              </div>
            )}
          </div>
        )}
      </section>
    </div>
  );
};

export default HomePage;
