package app.mosquito.appmosquito.appmosquito.Posts;

/**
 * Created by alex on 02/07/17.
 */

public enum EstadoAtual {

    FAZENDO {
        @Override
        public String toString() {
            return "fazendo";
        }
    }, FINALIZADO {
        @Override
        public String toString() {
            return "finalizado";
        }
    }
}
