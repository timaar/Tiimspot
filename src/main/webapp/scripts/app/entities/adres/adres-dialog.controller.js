'use strict';

angular.module('tiimspotApp').controller('AdresDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Adres',
        function($scope, $stateParams, $modalInstance, entity, Adres) {

        $scope.adres = entity;
        $scope.load = function(id) {
            Adres.get({id : id}, function(result) {
                $scope.adres = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('tiimspotApp:adresUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.adres.id != null) {
                Adres.update($scope.adres, onSaveSuccess, onSaveError);
            } else {
                Adres.save($scope.adres, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
