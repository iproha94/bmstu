function Perceptron(dimension) {
    let self = this;
    
    let learningRate = 0.1

    let weights = [];
    for (let i = 0; i < dimension; ++i) {
        weights[i] = Math.random() * 2 - 1;
    }

    self.response = function(x) {
        let y = 0;

        x.forEach((val, i) => {
            y += val * weights[i];
        })

        return y >= 0 ? 1 : -1;
    }

    self.updateWeights = function(x, err) {
        x.forEach((val, i) => {
            weights[i] += learningRate * err * val;
        })
    }

    self.training = function(data, maxIter) {
        let learned = false;
        let iteration = 0;

        while (!learned) {
            let globalError = 0;

            data.forEach((x) => {
                let r = self.response([x.growth, x.weight]);

                if (x.sex != r) {
                    let err = x.sex - r;
                    self.updateWeights([x.growth, x.weight], err);

                    globalError += Math.abs(err);
                }
            })

            iteration++;

            if (globalError == 0.0 || iteration >= maxIter) {
                learned = true;
                console.log("iteration = ", iteration);
            }
        }
    }
} 