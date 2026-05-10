import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { pgService } from '../services/api';
import { 
  MapPin, Star, User, Phone, Mail, 
  ArrowLeft, Share2, Heart, CheckCircle2,
  Coffee, Wifi, Shield, Tv, Wind, 
  ChevronRight, Calendar
} from 'lucide-react';

const PgDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [pg, setPg] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPg = async () => {
      try {
        if (id) {
          const response = await pgService.getPgById(id);
          setPg(response.data.data);
        }
      } catch (error) {
        console.error('Error fetching PG details:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchPg();
  }, [id]);

  if (loading) {
    return (
      <div className="pt-40 text-center flex flex-col items-center gap-4">
        <div className="w-12 h-12 border-4 border-primary/20 border-t-primary rounded-full animate-spin"></div>
        <p className="text-text-muted font-medium">Loading premium experience...</p>
      </div>
    );
  }

  if (!pg) {
    return <div className="pt-40 text-center text-xl font-bold">PG not found.</div>;
  }

  const imageUrl = `https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?auto=format&fit=crop&q=80&w=1200`;

  return (
    <div className="pt-32 pb-24 bg-gradient min-h-screen">
      <div className="container">
        {/* Navigation & Actions */}
        <div className="flex items-center justify-between mb-10 animate-fade-in">
          <Link to="/" className="flex items-center gap-2 text-text-muted hover:text-primary transition-all font-semibold group">
            <div className="w-8 h-8 rounded-full bg-surface-light flex items-center justify-center group-hover:bg-primary/10">
              <ArrowLeft size={18} />
            </div>
            <span>Back to explore</span>
          </Link>
          <div className="flex items-center gap-3">
            <button className="w-10 h-10 glass rounded-full flex items-center justify-center hover:bg-surface-hover transition-colors">
              <Share2 size={18} />
            </button>
            <button className="w-10 h-10 glass rounded-full flex items-center justify-center hover:bg-red-500/10 hover:text-red-400 transition-colors">
              <Heart size={18} />
            </button>
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12">
          {/* Main Content */}
          <div className="lg:col-span-8">
            <div className="rounded-[32px] overflow-hidden mb-12 h-[500px] relative shadow-2xl animate-fade-in delay-100">
              <img src={imageUrl} alt={pg.pgName} className="w-full h-full object-cover" />
              <div className="absolute top-6 left-6 flex gap-3">
                 <span className="glass px-4 py-2 rounded-full text-xs font-bold uppercase tracking-widest flex items-center gap-2">
                   <div className="w-2 h-2 rounded-full bg-green-500 animate-pulse"></div>
                   Verified Property
                 </span>
              </div>
            </div>

            <div className="animate-fade-in delay-200">
              <div className="flex flex-col md:flex-row md:items-center justify-between mb-6 gap-4">
                <div>
                  <h1 className="text-5xl font-bold mb-3 tracking-tight">{pg.pgName}</h1>
                  <div className="flex items-center gap-2 text-text-muted font-medium">
                    <MapPin size={18} className="text-primary" />
                    <span>{pg.pgAddress}, {pg.pgCity}, {pg.pgState}</span>
                  </div>
                </div>
                <div className="flex items-center gap-2 glass px-5 py-3 rounded-2xl border-primary/10 self-start">
                  <Star size={24} className="text-yellow-400 fill-yellow-400" />
                  <div>
                    <p className="font-bold text-lg leading-none">{pg.averageRating.toFixed(1)}</p>
                    <p className="text-[10px] text-text-muted uppercase tracking-wider font-bold">{pg.totalReviews} reviews</p>
                  </div>
                </div>
              </div>
              
              <div className="flex flex-wrap gap-3 mb-12">
                {pg.amenities.map((amenity: any) => (
                  <div key={amenity.id} className="flex items-center gap-2.5 bg-surface-light/50 px-5 py-2.5 rounded-2xl border border-border/50 hover:border-primary/30 transition-colors">
                    <CheckCircle2 size={16} className="text-primary" />
                    <span className="text-sm font-semibold">{amenity.name}</span>
                  </div>
                ))}
              </div>

              <div className="glass p-8 rounded-[32px] mb-12 border-glass-border">
                <h2 className="text-2xl font-bold mb-4">About this PG</h2>
                <p className="text-text-muted leading-relaxed text-lg">
                  {pg.description || "Welcome to a space where comfort meets convenience. This PG is designed for modern living, offering a peaceful environment with all the necessary amenities. Located in a prime area, you'll have easy access to public transport, shopping centers, and IT parks."}
                </p>
              </div>

              <h2 className="text-3xl font-bold mb-8">Available Accommodations</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                {pg.rooms?.map((room: any) => (
                  <div key={room.id} className="glass p-8 rounded-3xl border-glass-border flex flex-col group hover:bg-surface-light/20 transition-all">
                    <div className="flex justify-between items-start mb-6">
                      <div>
                        <div className="flex items-center gap-2 mb-1">
                           <User size={16} className="text-primary" />
                           <h3 className="font-bold text-xl">{room.sharingType} Sharing</h3>
                        </div>
                        <p className="text-text-muted text-sm font-medium flex items-center gap-2">
                           <Wind size={14} /> {room.ac ? "AC Room" : "Non-AC Room"}
                        </p>
                      </div>
                      <div className="text-right">
                        <p className="text-primary font-bold text-3xl">₹{room.rent}</p>
                        <p className="text-text-muted text-xs font-bold uppercase tracking-widest">monthly</p>
                      </div>
                    </div>
                    
                    <div className="space-y-3 mb-8">
                       <div className="flex items-center justify-between text-sm">
                         <span className="text-text-muted">Available Beds</span>
                         <span className="font-bold text-green-400">{room.availableBeds} units left</span>
                       </div>
                       <div className="w-full bg-surface h-1.5 rounded-full overflow-hidden">
                         <div className="bg-primary h-full rounded-full" style={{width: '60%'}}></div>
                       </div>
                    </div>

                    <button className="btn btn-primary w-full py-4 rounded-2xl text-lg group-hover:scale-[1.02] transition-transform">
                      Book This Room
                    </button>
                  </div>
                ))}
                {(!pg.rooms || pg.rooms.length === 0) && (
                   <div className="col-span-full py-12 text-center glass rounded-3xl border-dashed border-border">
                     <p className="text-text-muted font-medium">No rooms currently listed for this property.</p>
                   </div>
                )}
              </div>
            </div>
          </div>

          {/* Sidebar / Owner Info */}
          <div className="lg:col-span-4">
            <div className="sticky top-40 space-y-8 animate-fade-in delay-300">
              <div className="glass p-10 rounded-[40px] border-primary/10 shadow-2xl">
                <h2 className="text-2xl font-bold mb-8 flex items-center gap-3">
                   <User size={24} className="text-primary" />
                   Owner Details
                </h2>
                
                <div className="flex items-center gap-5 mb-10">
                  <div className="w-20 h-20 rounded-[28px] bg-gradient-to-br from-primary to-accent flex items-center justify-center text-white shadow-xl shadow-primary/20">
                    <User size={40} />
                  </div>
                  <div>
                    <h3 className="font-bold text-2xl tracking-tight">{pg.owner?.name || "Premium Host"}</h3>
                    <div className="flex items-center gap-1.5 text-text-muted text-sm mt-1">
                       <Shield size={14} className="text-green-500" />
                       <span className="font-bold">Verified Professional</span>
                    </div>
                  </div>
                </div>

                <div className="space-y-6 mb-10">
                  <div className="flex items-center gap-4 text-text p-4 rounded-2xl bg-surface-light/30 border border-border/50 hover:bg-surface-light/50 transition-colors">
                    <div className="w-10 h-10 rounded-xl bg-primary/10 flex items-center justify-center text-primary">
                      <Phone size={20} />
                    </div>
                    <span className="font-semibold">+91 98765 43210</span>
                  </div>
                  <div className="flex items-center gap-4 text-text p-4 rounded-2xl bg-surface-light/30 border border-border/50 hover:bg-surface-light/50 transition-colors">
                    <div className="w-10 h-10 rounded-xl bg-secondary/10 flex items-center justify-center text-secondary">
                      <Mail size={20} />
                    </div>
                    <span className="font-semibold">contact@pgfinder.com</span>
                  </div>
                </div>

                <div className="space-y-4">
                  <button className="btn btn-primary w-full py-5 rounded-2xl text-lg shadow-primary/20">
                    Direct Chat
                  </button>
                  <button className="btn btn-outline w-full py-5 rounded-2xl text-lg">
                    <Calendar size={20} />
                    Schedule Visit
                  </button>
                </div>
              </div>

              <div className="glass p-8 rounded-[32px] border-glass-border overflow-hidden">
                <h3 className="font-bold text-xl mb-6 flex items-center gap-2">
                   <MapPin size={20} className="text-primary" />
                   Location Map
                </h3>
                <div className="w-full h-56 bg-surface rounded-[24px] border border-border/50 flex flex-col items-center justify-center text-text-muted italic gap-3 relative group overflow-hidden">
                  <div className="absolute inset-0 bg-surface-hover opacity-0 group-hover:opacity-20 transition-opacity"></div>
                  <MapPin size={40} className="text-primary/20 group-hover:scale-110 transition-transform" />
                  <span className="font-semibold text-sm">Interactive Map Integration</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PgDetailPage;
