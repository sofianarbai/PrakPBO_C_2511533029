package pekan5;

public class RekeningGiro extends Rekening {
	private double batasOverdraft;

	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		// Memanggil Inisialisasi dasar dari superclass
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}

	// IPLEMENTASI POLIMORFISME (OVERRIDING)
	@Override
	public boolean tarikTunai(double nominal) {
		if (nominal < 10000) {
			System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000");
			return false;
		}
		else if (nominal > (saldo + batasOverdraft)) {
			System.out.println("Transaksi Gagal: Melebihi batas overdraft! Maksimal penarikan: Rp" + (saldo + batasOverdraft));
			return false;
		}
		else {
			saldo -= nominal;
			// Merekam riwayat
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
			
			riwayatTransaksi.add(trxBaru);
			System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
			return true;
		}
	}
	public double getBatasOverdraft(){
		return batasOverdraft; 
	}
}