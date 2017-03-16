package com.pricedog.pricedogapp.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import com.pricedog.pricedogapp.modelo.Descricao;

import java.util.ArrayList;
import java.util.List;

public class Produto implements Parcelable{
	private long id;
	private Descricao descricao;
	private String subdescricao;
	private List<Long> ean = new ArrayList<>();

	protected Produto(Parcel in) {
		id = in.readLong();
		descricao = (Descricao) in.readValue(Descricao.class.getClassLoader());
	}

	public Produto(){
		descricao = new Descricao();
	}

	public static final Creator<Produto> CREATOR = new Creator<Produto>() {
		@Override
		public Produto createFromParcel(Parcel in) {
			return new Produto(in);
		}

		@Override
		public Produto[] newArray(int size) {
			return new Produto[size];
		}
	};

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Descricao getDescricao() {
		return descricao;
	}
	public void setDescricao(Descricao descricao) {
		this.descricao = descricao;
	}

	public String getSubdescricao() {
		return subdescricao;
	}
	public void setSubdescricao(String subdescricao) {
		this.subdescricao = subdescricao;
	}

	public List<Long> getEan() {
		return ean;
	}
	public void setEan(List<Long> ean) {
		this.ean = ean;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeValue(descricao);
	}

	public String toString()  {
		String string = descricao.toString();

		if (subdescricao != null)
			string += " - " + subdescricao;

		return string;
	}
}