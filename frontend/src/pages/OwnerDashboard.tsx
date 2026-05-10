import React, { useEffect, useState } from 'react';
import { ownerService } from '../services/api';
import { Plus, Edit, Trash2, Home, Bed, MapPin, LayoutDashboard, Sparkles, X } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const OwnerDashboard: React.FC = () => {
  const { user } = useAuth();
  const [pgs, setPgs] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [showAddModal, setShowAddModal] = useState(false);

  useEffect(() => {
    fetchMyPgs();
  }, []);

  const fetchMyPgs = async () => {
    try {
      const response = await ownerService.getMyPgs();
      setPgs(response.data.data);
    } catch (error) {
      console.error('Error fetching owner PGs:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="pt-32 pb-20 min-h-screen bg-gradient">
      <div className="container">
        <div className="flex flex-col md:flex-row md:items-center justify-between mb-12 gap-6 animate-fade-in">
          <div>
            <div className="flex items-center gap-2 mb-2">
               <Sparkles size={18} className="text-primary" />
               <span className="text-xs font-bold uppercase tracking-widest text-primary">Management Portal</span>
            </div>
            <h1 className="text-4xl font-bold mb-2 tracking-tight">Welcome back, {user?.name || 'Owner'}</h1>
            <p className="text-text-muted font-medium">You have {pgs.length} active property listings</p>
          </div>
          <button 
            onClick={() => setShowAddModal(true)}
            className="btn btn-primary px-8 py-4 shadow-xl shadow-primary/20"
          >
            <Plus size={22} />
            <span>List New Property</span>
          </button>
        </div>

        {loading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
            {[1, 2, 3].map((n) => (
              <div key={n} className="bg-surface/50 h-64 rounded-[32px] animate-pulse border border-border"></div>
            ))}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10 animate-fade-in delay-100">
            {pgs.map((pg) => (
              <div key={pg.id} className="glass p-8 rounded-[32px] border-glass-border flex flex-col group hover:bg-surface-light/20 transition-all shadow-xl">
                <div className="flex justify-between items-start mb-6">
                  <div className="w-14 h-14 bg-gradient-to-br from-primary/20 to-accent/20 rounded-2xl flex items-center justify-center text-primary group-hover:scale-110 transition-transform">
                    <Home size={28} />
                  </div>
                  <div className="flex gap-2.5">
                    <button className="w-10 h-10 rounded-xl bg-surface-light flex items-center justify-center text-text-muted hover:text-primary hover:bg-primary/10 transition-all shadow-sm">
                      <Edit size={18} />
                    </button>
                    <button className="w-10 h-10 rounded-xl bg-surface-light flex items-center justify-center text-text-muted hover:text-red-400 hover:bg-red-400/10 transition-all shadow-sm">
                      <Trash2 size={18} />
                    </button>
                  </div>
                </div>
                
                <h3 className="text-2xl font-bold mb-2 group-hover:text-primary transition-colors tracking-tight">{pg.pgName}</h3>
                <div className="flex items-center gap-2 text-text-muted text-sm mb-8 font-medium">
                  <MapPin size={16} className="text-primary/70" />
                  <span>{pg.pgCity}, {pg.pgState}</span>
                </div>

                <div className="mt-auto pt-6 border-t border-border/50 flex items-center justify-between">
                  <div className="flex items-center gap-2 text-sm font-bold">
                    <Bed size={18} className="text-primary" />
                    <span>{pg.rooms?.length || 0} Room Types</span>
                  </div>
                  <span className={`px-4 py-1.5 rounded-full text-xs font-bold uppercase tracking-wider ${
                    pg.status === 'ACTIVE' ? 'bg-green-500/10 text-green-400 border border-green-500/20' : 'bg-yellow-500/10 text-yellow-400 border border-yellow-500/20'
                  }`}>
                    {pg.status}
                  </span>
                </div>
              </div>
            ))}

            {pgs.length === 0 && (
              <div className="col-span-full text-center py-32 glass rounded-[40px] border-dashed border-primary/20">
                <div className="w-24 h-24 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-8">
                  <LayoutDashboard size={40} className="text-primary" />
                </div>
                <h3 className="text-3xl font-bold mb-3 tracking-tight">No properties listed yet</h3>
                <p className="text-text-muted mb-10 max-w-md mx-auto font-medium text-lg">Start your journey as a host by adding your first property to the platform.</p>
                <button 
                  onClick={() => setShowAddModal(true)}
                  className="btn btn-primary px-10 py-4 text-lg shadow-xl shadow-primary/20"
                >
                  <Plus size={24} />
                  <span>List Your First PG</span>
                </button>
              </div>
            )}
          </div>
        )}
      </div>

      {/* Add Modal Placeholder */}
      {showAddModal && (
        <div className="fixed inset-0 z-[110] flex items-center justify-center p-4">
          <div className="absolute inset-0 bg-background/60 backdrop-blur-md animate-fade-in" onClick={() => setShowAddModal(false)}></div>
          <div className="glass w-full max-w-2xl p-12 rounded-[40px] relative z-10 border-glass-border shadow-2xl animate-fade-in">
            <button 
              onClick={() => setShowAddModal(false)}
              className="absolute top-8 right-8 text-text-muted hover:text-text transition-colors"
            >
              <X size={24} />
            </button>
            
            <div className="text-center mb-10">
               <div className="w-16 h-16 bg-primary/10 rounded-2xl flex items-center justify-center mx-auto mb-6">
                 <Home size={32} className="text-primary" />
               </div>
               <h2 className="text-4xl font-bold mb-3 tracking-tight">List New Property</h2>
               <p className="text-text-muted font-medium">Provide the essential details to reach thousands of seekers</p>
            </div>
            
            <div className="bg-surface/50 p-12 rounded-3xl border border-dashed border-border text-center mb-10">
               <p className="text-text-muted text-xl font-medium italic">Comprehensive listing form coming soon...</p>
            </div>

            <div className="flex justify-end gap-5">
              <button onClick={() => setShowAddModal(false)} className="btn btn-outline px-8">Cancel</button>
              <button className="btn btn-primary px-10">Get Started</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default OwnerDashboard;
