package sp.senai.br.mercapli.components;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressBarMeta {

    private ProgressBar progressBar;

    public ProgressBarMeta(View view, int progressBarID){
        progressBar = view.findViewById(progressBarID);
    }

    public ProgressBarMeta(ProgressBar progressBar){
        this.progressBar = progressBar;
    }

    public void atualizar(int progresso){
        progressBar.setProgress(progresso);

        if(progresso > 0){
            if(progresso < 60)
                progressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            else if(progresso >= 60 && progresso < 85)
                progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
            else
                progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }
    }

}
