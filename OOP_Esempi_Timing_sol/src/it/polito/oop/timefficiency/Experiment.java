package it.polito.oop.timefficiency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Experiment<D> {
	
	public static final int NUM_RIPETIZIONI_DEFAULT = 30;
	
	
	private Map<String,Consumer<D>> algoritmi = new HashMap<>();
	private Supplier<D> generator;
	private int ripetizioni = NUM_RIPETIZIONI_DEFAULT;
	private Map<String,List<Double>> risultati;
	private int chNome=10;
	private int chPlot=60;

	public void addAlgorithm(String name, Consumer<D> algorithm) {
		algoritmi.put(name, algorithm);
	}
	
	public void setDataGenerator(Supplier<D> generator) {
		this.generator = generator;
	}
	
	public Collection<String >algorithms(){
		return null;
	}



	public double perform(String name) {
		Consumer<D> algorithm = algoritmi.get(name);
		D dati = generator.get();
		long begin = System.nanoTime();
		algorithm.accept(dati);
		long end = System.nanoTime();
		return (end-begin)/1000000.0;
	}
	
	public int run() {
		D dati = generator.get();
		risultati = new HashMap<>();
	
		// Pre-riempe la mappa 
//		for(String n : algoritmi.keySet()) {
//			risultati.put(n, new ArrayList<>(ripetizioni));
//		}
		
		
		for(int i=0; i<ripetizioni; ++i) {
			
			for(String n : algoritmi.keySet()) {
				Consumer<D> algoritmo = algoritmi.get(n);
				long begin = System.nanoTime();
				algoritmo.accept(dati);
				long end = System.nanoTime();
//				risultati.get(n).add( (end-begin)/1000000.0 );
				// OPPURE per evitare di pre-riempire la mappa.
				risultati.compute(n, (s,l) -> {
					if(l==null) l = new ArrayList<>(ripetizioni);
					l.add((end-begin)/1000000.0);
					return l;
				});
			}
		}
		
		return ripetizioni;
	}
	
	public void setRepeat(int n) {
		ripetizioni = n;
	}

	public int getRepeat() {
		return ripetizioni;
	}
	
	public Map<String,List<Double>> getMeasures(){
		return risultati;
	}
	
	public void setPlotFormat(int nameWidth, int plotWidth) {
		this.chNome = nameWidth;
		this.chPlot = plotWidth;
	}
	
	public String plotInterval() {
		
		Map<String,DoubleSummaryStatistics> stat = new HashMap<>();
		for(String n : risultati.keySet()) {
			stat.put(n, risultati.get(n).stream().
								  mapToDouble( x -> x ).
								  summaryStatistics() );
		}
		// OPPURE
		stat = 
		risultati.entrySet().stream().
		collect( toMap(Map.Entry::getKey,
					     e -> e.getValue().stream().
					     		mapToDouble( x -> x ).
					     		summaryStatistics()) );
		
		final DoubleSummaryStatistics globalStat = 
				risultati.values().stream().
				flatMap(List::stream).
				mapToDouble( x -> x ).
				summaryStatistics();
		// O, in alternativa
		double globalMin = stat.values().stream().
				//mapToDouble(DoubleSummaryStatistics::getMin).
				mapToDouble( dss -> dss.getMin() ).
				min().getAsDouble();
		double globalMax = stat.values().stream().
				mapToDouble( dss -> dss.getMax() ).
				max().getAsDouble();
		
		DoubleToIntFunction m = x ->  (int)( (x-globalStat.getMin())/(globalStat.getMax()-globalStat.getMin()) * (this.chPlot-1) );
		
		double x = globalMax;
		m.applyAsInt(x);  // sfrutta la closure che include globalStat
		// Più compatto rispetto a:
		mtc(x,globalStat.getMin(),globalStat.getMax());
		
		String result = 
		stat.entrySet().stream().
			map( e -> {
				String res = String.format("%"+chNome+"."+chNome+"s : ", e.getKey());
				int min = m.applyAsInt(e.getValue().getMin());
				int avg = m.applyAsInt(e.getValue().getAverage());
				int max = m.applyAsInt(e.getValue().getMax());
				if(min>0) {
					res += nvolte(' ',min);
				}
				if(min<avg) {
					res+="<";
					res+=nvolte('-',avg-min-1);
				}
				res += "|";
				if(max>avg) {
					res+=nvolte('-',max-avg-1);
					res+=">";
				}
				return res;
			}).collect(joining("\n"));
		// Aggiunge l'asse con gli estremi
		// 1. tacche
		result += "\n" + nvolte(' ',chNome+3) + "^" + nvolte(' ',chPlot-2) + "^";
		// 2. valori (min e max)
		result += "\n" + nvolte(' ',chNome+1) + 
				String.format("%5.2f", globalStat.getMin()) +
				nvolte(' ',chPlot-6) +
				String.format("%5.2f", globalStat.getMax())
				;
		return result;
	}
	
	private int mtc(double x, double min, double max) {
		return (int)( (x-min)/(max-min) * this.chPlot );
	}
	
	public static String nvolte(char ch, long n) {
		if(n<=0) return "";
		char[] buffer = new char[(int)n];
		Arrays.fill(buffer, ch);
		return new String(buffer);
	}

	
}
