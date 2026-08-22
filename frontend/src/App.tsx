import { useEffect, useState } from 'react';
import { Activity, Car, IndianRupee, Landmark, RefreshCw, ShieldCheck, WalletCards } from 'lucide-react';

const API = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

type Plaza = { id:number; code:string; name:string; highway:string; location:string; laneCount:number };
type Vehicle = { id:number; registrationNumber:string; fastagId:string; vehicleClass:string; ownerName:string };
type Transaction = { id:number; reference:string; amount:number; status:string; laneNumber:string; transactionTime:string; vehicle?:Vehicle; plaza?:Plaza };

export default function App() {
  const [plazas, setPlazas] = useState<Plaza[]>([]);
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [registration, setRegistration] = useState('AP37AB1234');
  const [plazaId, setPlazaId] = useState<number | ''>('');
  const [lane, setLane] = useState('L1');
  const [message, setMessage] = useState('');

  const load = async () => {
    const [p,v,t] = await Promise.all([
      fetch(`${API}/plazas`).then(r=>r.json()), fetch(`${API}/vehicles`).then(r=>r.json()), fetch(`${API}/transactions/recent`).then(r=>r.json())
    ]);
    setPlazas(p); setVehicles(v); setTransactions(t); if (p.length && plazaId === '') setPlazaId(p[0].id);
  };
  useEffect(() => { load().catch(e => setMessage(`Backend unavailable: ${e.message}`)); }, []);

  const processToll = async () => {
    setMessage('Processing toll...');
    const response = await fetch(`${API}/transactions/process`, { method:'POST', headers:{'Content-Type':'application/json'}, body:JSON.stringify({registrationNumber:registration, plazaId:Number(plazaId), laneNumber:lane}) });
    const data = await response.json();
    setMessage(response.ok ? `${data.status}: ₹${data.amount} — ${data.reference}` : (data.message || 'Transaction failed'));
    await load();
  };

  const successCount = transactions.filter(t=>t.status==='SUCCESS').length;
  const revenue = transactions.filter(t=>t.status==='SUCCESS').reduce((s,t)=>s+Number(t.amount||0),0);

  return <div className="app">
    <header className="topbar"><div className="brand"><Landmark size={26}/><div><strong>TollFlow</strong><span>Management Platform</span></div></div><div className="status"><span className="dot"/> System Operational <button onClick={()=>load()}><RefreshCw size={15}/> Refresh</button></div></header>
    <main>
      <section className="hero"><div><p className="eyebrow">CONTROL CENTER</p><h1>Toll operations dashboard</h1><p>Monitor plazas, vehicles and toll transactions from one place.</p></div><div className="badge"><ShieldCheck size={18}/> Production-style demo</div></section>
      <section className="stats">
        <div className="stat"><span><IndianRupee/></span><div><small>Revenue</small><b>₹{revenue.toLocaleString('en-IN')}</b></div></div>
        <div className="stat"><span><Activity/></span><div><small>Successful transactions</small><b>{successCount}</b></div></div>
        <div className="stat"><span><Car/></span><div><small>Registered vehicles</small><b>{vehicles.length}</b></div></div>
        <div className="stat"><span><Landmark/></span><div><small>Active plazas</small><b>{plazas.filter(p=>true).length}</b></div></div>
      </section>
      <section className="grid">
        <div className="card process"><div className="cardhead"><div><h2>Process toll</h2><p>Simulate a lane transaction</p></div><WalletCards/></div>
          <label>Vehicle registration<input value={registration} onChange={e=>setRegistration(e.target.value)} placeholder="AP37AB1234"/></label>
          <div className="two"><label>Toll plaza<select value={plazaId} onChange={e=>setPlazaId(Number(e.target.value))}>{plazas.map(p=><option key={p.id} value={p.id}>{p.code} — {p.name}</option>)}</select></label><label>Lane<input value={lane} onChange={e=>setLane(e.target.value)}/></label></div>
          <button className="primary" onClick={processToll} disabled={!plazaId}><WalletCards size={17}/> Process toll payment</button>
          {message && <div className="message">{message}</div>}
        </div>
        <div className="card"><div className="cardhead"><div><h2>Toll plazas</h2><p>Configured operational locations</p></div><Landmark/></div><div className="list">{plazas.map(p=><div className="row" key={p.id}><div><b>{p.name}</b><small>{p.highway} · {p.location}</small></div><strong>{p.laneCount} lanes</strong></div>)}</div></div>
      </section>
      <section className="card"><div className="cardhead"><div><h2>Recent transactions</h2><p>Latest 100 toll events</p></div><Activity/></div><div className="tablewrap"><table><thead><tr><th>Reference</th><th>Vehicle</th><th>Plaza</th><th>Lane</th><th>Amount</th><th>Status</th><th>Time</th></tr></thead><tbody>{transactions.slice(0,12).map(t=><tr key={t.id}><td>{t.reference}</td><td>{t.vehicle?.registrationNumber || '—'}</td><td>{t.plaza?.code || '—'}</td><td>{t.laneNumber}</td><td>₹{Number(t.amount).toFixed(2)}</td><td><span className={`pill ${t.status.toLowerCase()}`}>{t.status}</span></td><td>{new Date(t.transactionTime).toLocaleString()}</td></tr>)}</tbody></table></div></section>
    </main>
  </div>;
}
